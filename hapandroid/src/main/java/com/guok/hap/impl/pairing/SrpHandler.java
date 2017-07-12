package com.guok.hap.impl.pairing;

import com.guok.hap.impl.http.HttpResponse;
import com.guok.hap.impl.pairing.HomekitSRP6ServerSession.State;
import com.guok.hap.impl.pairing.PairSetupRequest.Stage2Request;
import com.guok.hap.impl.pairing.TypeLengthValueUtils.Encoder;
import com.guok.hap.impl.responses.GeneralErrorResponse;
import com.guok.hap.impl.responses.HttpStatusCodes;
import com.nimbusds.srp6.SRP6CryptoParams;
import com.nimbusds.srp6.SRP6Exception;
import com.nimbusds.srp6.SRP6Routines;
import com.nimbusds.srp6.SRP6VerifierGenerator;
import com.nimbusds.srp6.XRoutineWithUserIdentity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * Handle M1 and M3 of Pair Setup.
 * <p>Secure Remote Password (SRP) RFC5054.
 * SRP C API defined by http://srp.stanford.edu/
 * SHA-512 is used as the hash function, replacing SHA-1.
 * Modulus N and Generator G are specified by th 3072-bit group of RFC5054
 */
class SrpHandler {

	//	Precomputed safe 3072 bit prime 'N'. Origin RFC 5054, appendix A.
	private final static BigInteger N_3072 = new BigInteger("5809605995369958062791915965639201402176612226902900533702900882779736177890990861472094774477339581147373410185646378328043729800750470098210924487866935059164371588168047540943981644516632755067501626434556398193186628990071248660819361205119793693985433297036118232914410171876807536457391277857011849897410207519105333355801121109356897459426271845471397952675959440793493071628394122780510124618488232602464649876850458861245784240929258426287699705312584509625419513463605155428017165714465363094021609290561084025893662561222573202082865797821865270991145082200656978177192827024538990239969175546190770645685893438011714430426409338676314743571154537142031573004276428701433036381801705308659830751190352946025482059931306571004727362479688415574702596946457770284148435989129632853918392117997472632693078113129886487399347796982772784615865232621289656944284216824611318709764535152507354116344703769998514148343807");
	private final static BigInteger G = BigInteger.valueOf(5);
	private final static String IDENTIFIER = "Pair-Setup";
	
	private final static Logger logger = LoggerFactory.getLogger(SrpHandler.class);
	
	private final BigInteger salt ;//General 16 byte of random salt
	private final HomekitSRP6ServerSession SrpSession;
	private final SRP6CryptoParams config;
	private final String pin;
	
	public SrpHandler(String pin) {
		config = new SRP6CryptoParams(N_3072, G, "SHA-512");
		SrpSession = new HomekitSRP6ServerSession(config);
		SrpSession.setClientEvidenceRoutine(new ClientEvidenceRoutineImpl());
		SrpSession.setServerEvidenceRoutine(new ServerEvidenceRoutineImpl());
		this.pin = pin;
		this.salt = new BigInteger(SRP6Routines.generateRandomSalt(16));
	}
	
	public HttpResponse handle(PairSetupRequest request) throws Exception {
		switch(request.getStage()) {
		case ONE:
			return step1();
			
		case TWO:
			return step2((Stage2Request) request);
			
		default:
			return new GeneralErrorResponse(HttpStatusCodes.NOT_FOUND);
		}
	}

	/**
	 * Response of M1 with kTLVType_State, kTLVType_PublicKey, kTLVType_Salt.
	 * kTLVType_State is M2. kTLVType_PublicKey is Accessory's SPR public key.
	 * verify process does not implement.
	 *
	 * @return response of M1
	 * @throws Exception
	 */
	private HttpResponse step1() throws Exception {
		if (SrpSession.getState() != State.INIT) {
			String err = "Session is not in state INIT when receiving step1";
			return TypeLengthValueUtils.createTLVErrorResponse(err, TLVState.M2.getKey(), TLVError.BUSY);
		}
				
		SRP6VerifierGenerator verifierGenerator = new SRP6VerifierGenerator(config);
		verifierGenerator.setXRoutine(new XRoutineWithUserIdentity());
		BigInteger verifier = verifierGenerator.generateVerifier(salt, IDENTIFIER, pin);
		BigInteger SrpPublicKey = SrpSession.step1(IDENTIFIER, salt, verifier);

		Encoder encoder = TypeLengthValueUtils.getEncoder();
		encoder.add(MessageType.STATE, TLVState.M2.getKey());
		encoder.add(MessageType.SALT, salt);
		encoder.add(MessageType.PUBLIC_KEY, SrpPublicKey);
		return new PairingResponse(encoder.toByteArray());
	}

	/**
	 * Response of M3 with kTLVType_State, kTLVType_Proof.
	 * kTLVType_State is M4. kTLVType_Proof is accessory's SRP proof
	 * verify process does not implement.
	 * @param request response of M3
	 * @return
	 * @throws Exception
	 */
	private HttpResponse step2(Stage2Request request) throws Exception {
		if (SrpSession.getState() != State.STEP_1) {
			String err = "Session is not in state Stage 1 when receiving step2";
			return TypeLengthValueUtils.createTLVErrorResponse(err, TLVState.M4.getKey(), TLVError.BUSY);
		}
		BigInteger accessorySrpProof = null;
		try {
			accessorySrpProof = SrpSession.step2(request.getPublicKey(), request.getProof());
		} catch (SRP6Exception e) {
			return TypeLengthValueUtils.createTLVErrorResponse(e.getMessage(), TLVState.M4.getKey(), TLVError.AUTHENTICATION);
		}
		Encoder encoder = TypeLengthValueUtils.getEncoder();
		encoder.add(MessageType.STATE, TLVState.M4.getKey());
		encoder.add(MessageType.PROOF, accessorySrpProof);
		return new PairingResponse(encoder.toByteArray());
	}

	/**
	 * compute SRP shared secret key
	 * @return
	 */
	byte[] getK() {
		MessageDigest digest = SrpSession.getCryptoParams().getMessageDigestInstance();
		BigInteger S = SrpSession.getSessionKey(false);
		byte[] sBytes = bigIntegerToUnsignedByteArray(S);
		return digest.digest(sBytes);
	}

	static byte[] bigIntegerToUnsignedByteArray(BigInteger i) {
		byte[] array = i.toByteArray();
		if (array[0] == 0) {
			array = Arrays.copyOfRange(array, 1, array.length);
		}
		return array;
	}
	
}
