package com.guok.hap.impl.crypto;

import net.i2p.crypto.eddsa.EdDSAEngine;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveTable;
import net.i2p.crypto.eddsa.spec.EdDSAParameterSpec;
import net.i2p.crypto.eddsa.spec.EdDSAPublicKeySpec;

import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.Signature;

public class EdDSAVerifier {
	
	private final PublicKey publicKey;

	public EdDSAVerifier(byte[] publicKey) {
		EdDSAParameterSpec spec = EdDSANamedCurveTable.getByName("ed25519-sha-512");
		EdDSAPublicKeySpec pubKey = new EdDSAPublicKeySpec(publicKey, spec);
		this.publicKey = new EdDSAPublicKey(pubKey);
	}
	
	public boolean verify(byte[] data, byte[] signature) throws Exception {
		Signature sgr = new EdDSAEngine(MessageDigest.getInstance("SHA-512"));
        sgr.initVerify(publicKey);
        sgr.update(data);
        
		return sgr.verify(signature);
	}

}
