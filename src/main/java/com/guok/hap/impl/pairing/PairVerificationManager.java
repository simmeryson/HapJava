package com.guok.hap.impl.pairing;

import com.guok.hap.HomekitAuthInfo;
import com.guok.hap.impl.HomekitRegistry;
import com.guok.hap.impl.crypto.ChachaDecoder;
import com.guok.hap.impl.crypto.ChachaEncoder;
import com.guok.hap.impl.crypto.EdsaSigner;
import com.guok.hap.impl.crypto.EdsaVerifier;
import com.guok.hap.impl.http.HttpRequest;
import com.guok.hap.impl.http.HttpResponse;
import com.guok.hap.impl.pairing.PairVerificationRequest.Stage1Request;
import com.guok.hap.impl.pairing.PairVerificationRequest.Stage2Request;
import com.guok.hap.impl.pairing.TypeLengthValueUtils.DecodeResult;
import com.guok.hap.impl.pairing.TypeLengthValueUtils.Encoder;
import com.guok.hap.impl.responses.GeneralErrorResponse;
import com.guok.hap.impl.responses.HttpStatusCodes;
import com.guok.hap.impl.responses.OkResponse;

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

	private final HomekitAuthInfo authInfo;
	private final HomekitRegistry registry;
	
	private byte[] hkdfKey;
	private byte[] iOSCurve25519PublicKey;
	private byte[] publicKey;
	private byte[] sharedSecret;
	
	public PairVerificationManager(HomekitAuthInfo authInfo, HomekitRegistry registry) {
		this.authInfo = authInfo;
		this.registry = registry;
	}

	public HttpResponse handle(HttpRequest rawRequest) throws Exception {
		PairVerificationRequest request = PairVerificationRequest.of(rawRequest.getBody());
		switch(request.getStage()) {
		case ONE:
			return stage1((Stage1Request) request);
			
		case TWO:
			return stage2((Stage2Request) request);
			
		default:
			return new GeneralErrorResponse(HttpStatusCodes.NOT_FOUND);
		}
	}

	private HttpResponse stage1(Stage1Request request) throws Exception {
		logger.debug("Starting pair verification for "+registry.getLabel());
		iOSCurve25519PublicKey = request.getClientPublicKey();
		publicKey = new byte[32];
		byte[] privateKey = new byte[32];
		getSecureRandom().nextBytes(privateKey);
		Curve25519.keygen(publicKey, null, privateKey);
		
		sharedSecret = new byte[32];
		Curve25519.curve(sharedSecret, privateKey, iOSCurve25519PublicKey);
		
		byte[] material = ByteUtils.joinBytes(publicKey, authInfo.getMac().getBytes(StandardCharsets.UTF_8),
				iOSCurve25519PublicKey);
		
		byte[] signature = new EdsaSigner(authInfo.getPrivateKey()).sign(material);
		
		HKDFBytesGenerator hkdf = new HKDFBytesGenerator(new SHA512Digest());
		hkdf.init(new HKDFParameters(sharedSecret, "Pair-Verify-Encrypt-Salt".getBytes(StandardCharsets.UTF_8),
				"Pair-Verify-Encrypt-Info".getBytes(StandardCharsets.UTF_8)));
		hkdfKey = new byte[32];
		hkdf.generateBytes(hkdfKey, 0, 32);
		
		Encoder encoder = TypeLengthValueUtils.getEncoder();
		encoder.add(MessageType.IDENTIFIER, authInfo.getMac().getBytes(StandardCharsets.UTF_8));
		encoder.add(MessageType.SIGNATURE, signature);
		byte[] plaintext = encoder.toByteArray();
		
		ChachaEncoder chacha = new ChachaEncoder(hkdfKey, "PV-Msg02".getBytes(StandardCharsets.UTF_8));
		byte[] ciphertext = chacha.encodeCiphertext(plaintext);
		
		encoder = TypeLengthValueUtils.getEncoder();
		encoder.add(MessageType.STATE, (short) 2);
		encoder.add(MessageType.ENCRYPTED_DATA, ciphertext);
		encoder.add(MessageType.PUBLIC_KEY, publicKey);
		return new PairingResponse(encoder.toByteArray());
	}
	
	private HttpResponse stage2(Stage2Request request) throws Exception {
		ChachaDecoder chacha = new ChachaDecoder(hkdfKey, "PV-Msg03".getBytes(StandardCharsets.UTF_8));
		byte[] plaintext = chacha.decodeCiphertext(request.getAuthTagData(), request.getMessageData());
		
		DecodeResult d = TypeLengthValueUtils.decode(plaintext);
		byte[] iOSPairingID = d.getBytes(MessageType.IDENTIFIER);
		byte[] clientSignature = d.getBytes(MessageType.SIGNATURE);
		
		byte[] material = ByteUtils.joinBytes(iOSCurve25519PublicKey, iOSPairingID, publicKey);
		
		byte[] clientLtpk = authInfo.getUserPublicKey(new String(iOSPairingID, StandardCharsets.UTF_8));
		if (clientLtpk == null) {
			throw new Exception("Unknown user: "+new String(iOSPairingID, StandardCharsets.UTF_8));
		}
		
		Encoder encoder = TypeLengthValueUtils.getEncoder();
		if (new EdsaVerifier(clientLtpk).verify(material, clientSignature)) {
			encoder.add(MessageType.STATE, (short) 4);
			logger.debug("Completed pair verification for "+registry.getLabel());
			return new UpgradeResponse(encoder.toByteArray(), createKey("Control-Write-Encryption-Key"),
					createKey("Control-Read-Encryption-Key"));
		} else {
			encoder.add(MessageType.ERROR, (short) 4);
			logger.warn("Invalid signature. Could not pair "+registry.getLabel());
			return new OkResponse(encoder.toByteArray());
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
			synchronized(PairVerificationManager.class) {
				if (secureRandom == null) {
					secureRandom = new SecureRandom();
				}
			}
		}
		return secureRandom;
	}

}
