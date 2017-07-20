package com.guok.hap.impl.pairing;

import com.guok.hap.BridgeAuthInfo;
import com.guok.hap.impl.advertiser.IAdvertiser;
import com.guok.hap.impl.crypto.ChachaDecoder;
import com.guok.hap.impl.crypto.ChachaEncoder;
import com.guok.hap.impl.crypto.EdDSASigner;
import com.guok.hap.impl.crypto.EdDSAVerifier;
import com.guok.hap.impl.http.HttpResponse;
import com.guok.hap.impl.pairing.PairSetupRequest.Stage3Request;
import com.guok.hap.impl.pairing.TypeLengthValueUtils.DecodeResult;
import com.guok.hap.impl.pairing.TypeLengthValueUtils.Encoder;

import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.generators.HKDFBytesGenerator;
import org.bouncycastle.crypto.params.HKDFParameters;

import java.nio.charset.StandardCharsets;

/**
 * Handle M5 of Pair Setup.
 */
class FinalPairHandler {

	private final byte[] SrpSharedSecret;//SRP shared secret key
	private final BridgeAuthInfo authInfo;
	private final IAdvertiser advertiser;

	private byte[] hkdf_session_key;

	public FinalPairHandler(byte[] SrpSharedSecret, BridgeAuthInfo authInfo, IAdvertiser advertiser) {
		this.SrpSharedSecret = SrpSharedSecret;
		this.authInfo = authInfo;
		this.advertiser = advertiser;
	}

	public HttpResponse handle(PairSetupRequest req) throws Exception {
		HKDFBytesGenerator hkdf = new HKDFBytesGenerator(new SHA512Digest());
		hkdf.init(new HKDFParameters(SrpSharedSecret, "Pair-Setup-Encrypt-Salt".getBytes(StandardCharsets.UTF_8),
				"Pair-Setup-Encrypt-Info".getBytes(StandardCharsets.UTF_8)));
		byte[] sessionKey = hkdf_session_key = new byte[32];
		hkdf.generateBytes(sessionKey, 0, 32);

		return decrypt((Stage3Request) req, sessionKey);
	}

	private HttpResponse decrypt(Stage3Request req, byte[] sessionKey) throws Exception {
		ChachaDecoder chacha = new ChachaDecoder(sessionKey, "PS-Msg05".getBytes(StandardCharsets.UTF_8));
		byte[] authTag = chacha.decodeCiphertext(req.getAuthTagData(), req.getMessageData());

		DecodeResult d = TypeLengthValueUtils.decode(authTag);
		byte[] iOSDevicePairingID = d.getBytes(MessageType.IDENTIFIER);
		byte[] iOSCurveLTPK = d.getBytes(MessageType.PUBLIC_KEY);
		byte[] proof = d.getBytes(MessageType.SIGNATURE);
		return verifyM5(iOSDevicePairingID, iOSCurveLTPK, proof);
	}

	private HttpResponse verifyM5(byte[] iOSDevicePairingID, byte[] iOSCurveLTPK, byte[] proof) throws Exception {
		HKDFBytesGenerator hkdf = new HKDFBytesGenerator(new SHA512Digest());
		hkdf.init(new HKDFParameters(SrpSharedSecret, "Pair-Setup-Controller-Sign-Salt".getBytes(StandardCharsets.UTF_8),
				"Pair-Setup-Controller-Sign-Info".getBytes(StandardCharsets.UTF_8)));
		byte[] iOSDeviceX = new byte[32];
		hkdf.generateBytes(iOSDeviceX, 0, 32);

		byte[] iOSDeviceInfo = ByteUtils.joinBytes(iOSDeviceX, iOSDevicePairingID, iOSCurveLTPK);

		if (!new EdDSAVerifier(iOSCurveLTPK).verify(iOSDeviceInfo, proof)) {
			String err = "Final pair setup verify fail in M5 from iOS";
			return TypeLengthValueUtils.createTLVErrorResponse(err, TLVState.M6.getKey(), TLVError.AUTHENTICATION);
		}
		authInfo.createUser(new String(iOSDevicePairingID, StandardCharsets.UTF_8), iOSCurveLTPK);
		advertiser.setDiscoverable(false);//gk
		return createM6Response();
	}

	private HttpResponse createM6Response() throws Exception {
		HKDFBytesGenerator hkdf = new HKDFBytesGenerator(new SHA512Digest());
		hkdf.init(new HKDFParameters(SrpSharedSecret, "Pair-Setup-Accessory-Sign-Salt".getBytes(StandardCharsets.UTF_8),
				"Pair-Setup-Accessory-Sign-Info".getBytes(StandardCharsets.UTF_8)));
		byte[] accessoryX = new byte[32];
		hkdf.generateBytes(accessoryX, 0, 32);

		EdDSASigner signer = new EdDSASigner(authInfo.getPrivateKey());

		byte[] accessoryInfo = ByteUtils.joinBytes(accessoryX, authInfo.getMac().getBytes(StandardCharsets.UTF_8), signer.getPublicKey());

		byte[] signature = signer.sign(accessoryInfo);

		Encoder encoder = TypeLengthValueUtils.getEncoder();
		encoder.add(MessageType.IDENTIFIER, authInfo.getMac().getBytes(StandardCharsets.UTF_8));
		encoder.add(MessageType.PUBLIC_KEY, signer.getPublicKey());
		encoder.add(MessageType.SIGNATURE, signature);
		byte[] authTag = encoder.toByteArray();

		ChachaEncoder chacha = new ChachaEncoder(hkdf_session_key, "PS-Msg06".getBytes(StandardCharsets.UTF_8));
		byte[] ciphertext = chacha.encodeCiphertext(authTag);

		encoder = TypeLengthValueUtils.getEncoder();
		encoder.add(MessageType.STATE, TLVState.M6.getKey());
		encoder.add(MessageType.ENCRYPTED_DATA, ciphertext);

		return new PairingResponse(encoder.toByteArray());
	}

}
