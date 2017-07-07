package com.guok.hap.impl.pairing;

import com.guok.hap.BridgeAuthInfo;
import com.guok.hap.impl.HomekitRegistry;
import com.guok.hap.impl.crypto.ChachaDecoder;
import com.guok.hap.impl.crypto.ChachaEncoder;
import com.guok.hap.impl.crypto.EdDSASigner;
import com.guok.hap.impl.crypto.EdDSAVerifier;
import com.guok.hap.impl.http.HttpRequest;
import com.guok.hap.impl.http.HttpResponse;
import com.guok.hap.impl.pairing.PairVerificationRequest.Stage1Request;
import com.guok.hap.impl.pairing.PairVerificationRequest.Stage2Request;
import com.guok.hap.impl.pairing.TypeLengthValueUtils.DecodeResult;
import com.guok.hap.impl.pairing.TypeLengthValueUtils.Encoder;
import com.guok.hap.impl.responses.GeneralErrorResponse;
import com.guok.hap.impl.responses.HttpStatusCodes;

import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.generators.HKDFBytesGenerator;
import org.bouncycastle.crypto.params.HKDFParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

import djb.Curve25519;

/**
 * Once a pairing has been established , it must be verified each time it is used.
 * All Pair Verify process contains 4 steps. Accessory handle the M1, M3
 */
public class PairVerificationManager {

    private final static Logger logger = LoggerFactory.getLogger(PairVerificationManager.class);
    private static volatile SecureRandom secureRandom;

    private final BridgeAuthInfo authInfo;
    private final HomekitRegistry registry;

    private byte[] sessionKey;
    private byte[] iOSCurve25519PublicKey;
    private byte[] AccessoryCurve25519PublicKey;
    private byte[] sharedSecret;

    public PairVerificationManager(BridgeAuthInfo authInfo, HomekitRegistry registry) {
        this.authInfo = authInfo;
        this.registry = registry;
    }

    public HttpResponse handle(HttpRequest rawRequest) throws Exception {
        PairVerificationRequest request = PairVerificationRequest.of(rawRequest.getBody());
        switch (request.getStage()) {
            case ONE:
                return stage1((Stage1Request) request);

            case TWO:
                return stage2((Stage2Request) request);

            default:
                return new GeneralErrorResponse(HttpStatusCodes.NOT_FOUND);
        }
    }

    private HttpResponse stage1(Stage1Request request) throws Exception {
        logger.debug("Starting pair verification for " + registry.getLabel());
        iOSCurve25519PublicKey = request.getClientPublicKey();

        AccessoryCurve25519PublicKey = new byte[32];
        byte[] privateKey = new byte[32];
        getSecureRandom().nextBytes(privateKey);
        Curve25519.keygen(AccessoryCurve25519PublicKey, null, privateKey);

        sharedSecret = new byte[32];
        Curve25519.curve(sharedSecret, privateKey, iOSCurve25519PublicKey);

        byte[] accessoryInfo = ByteUtils.joinBytes(AccessoryCurve25519PublicKey,
                authInfo.getMac().getBytes(StandardCharsets.UTF_8),
                iOSCurve25519PublicKey);

        logger.error("accessoryID: " + authInfo.getMac());
        byte[] accessorySignature = new EdDSASigner(authInfo.getPrivateKey()).sign(accessoryInfo);
        Encoder encoder = TypeLengthValueUtils.getEncoder();
        encoder.add(MessageType.IDENTIFIER, authInfo.getMac().getBytes(StandardCharsets.UTF_8));
        encoder.add(MessageType.SIGNATURE, accessorySignature);
        byte[] plaintext = encoder.toByteArray();

        HKDFBytesGenerator hkdf = new HKDFBytesGenerator(new SHA512Digest());
        hkdf.init(new HKDFParameters(sharedSecret, "Pair-Verify-Encrypt-Salt".getBytes(StandardCharsets.UTF_8),
                "Pair-Verify-Encrypt-Info".getBytes(StandardCharsets.UTF_8)));
        sessionKey = new byte[32];
        hkdf.generateBytes(sessionKey, 0, 32);

        ChachaEncoder chacha = new ChachaEncoder(sessionKey, "PV-Msg02".getBytes(StandardCharsets.UTF_8));
        byte[] authTag = chacha.encodeCiphertext(plaintext);

        encoder = TypeLengthValueUtils.getEncoder();
        encoder.add(MessageType.STATE, TLVState.M2.getKey());
        encoder.add(MessageType.ENCRYPTED_DATA, authTag);
        encoder.add(MessageType.PUBLIC_KEY, AccessoryCurve25519PublicKey);

        return new PairingResponse(encoder.toByteArray());
    }

    private HttpResponse stage2(Stage2Request request) throws Exception {
        if (request.getAuthTagData().length == 0 || request.getMessageData().length == 0) {
            String error = "no EncryptedData in pair verify request!";
            return TypeLengthValueUtils.createTLVErrorResponse(error, TLVState.M4.getKey(), TLVError.AUTHENTICATION);
        }
        ChachaDecoder chacha = new ChachaDecoder(sessionKey, "PV-Msg03".getBytes(StandardCharsets.UTF_8));
        byte[] plaintext = chacha.decodeCiphertext(request.getAuthTagData(), request.getMessageData());

        DecodeResult d = TypeLengthValueUtils.decode(plaintext);
        byte[] iOSPairingID = d.getBytes(MessageType.IDENTIFIER);
        byte[] iOSDeviceSignature = d.getBytes(MessageType.SIGNATURE);

        logger.error("iOSPairingID: " + new String(iOSPairingID, StandardCharsets.UTF_8));
        byte[] iOSDeviceInfo = ByteUtils.joinBytes(iOSCurve25519PublicKey, iOSPairingID, AccessoryCurve25519PublicKey);

        byte[] iOSDeviceLtpk = authInfo.getUserPublicKey(new String(iOSPairingID, StandardCharsets.UTF_8));
        if (iOSDeviceLtpk == null) {
            String error = "Unknown user: " + new String(iOSPairingID, StandardCharsets.UTF_8);
            return TypeLengthValueUtils.createTLVErrorResponse(error, TLVState.M4.getKey(), TLVError.AUTHENTICATION);
        }

        Encoder encoder = TypeLengthValueUtils.getEncoder();
        if (new EdDSAVerifier(iOSDeviceLtpk).verify(iOSDeviceInfo, iOSDeviceSignature)) {
            encoder.add(MessageType.STATE, TLVState.M4.getKey());
            logger.info("Completed pair verification for " + registry.getLabel());
            return new UpgradeResponse(encoder.toByteArray(), createKey("Control-Write-Encryption-Key"),
                    createKey("Control-Read-Encryption-Key"));
        } else {
            String error = "Invalid signature. Could not pair " + registry.getLabel();
            return TypeLengthValueUtils.createTLVErrorResponse(error, TLVState.M4.getKey(), TLVError.AUTHENTICATION);
        }
    }

    private byte[] createKey(String info) {
        HKDFBytesGenerator hkdf = new HKDFBytesGenerator(new SHA512Digest());
        hkdf.init(new HKDFParameters(sharedSecret, "Control-Salt".getBytes(StandardCharsets.UTF_8),
                info.getBytes(StandardCharsets.UTF_8)));
        byte[] key = new byte[32];
        hkdf.generateBytes(key, 0, 32);
        return key;
    }

    private static SecureRandom getSecureRandom() {
        if (secureRandom == null) {
            synchronized (PairVerificationManager.class) {
                if (secureRandom == null) {
                    secureRandom = new SecureRandom();
                }
            }
        }
        return secureRandom;
    }

}
