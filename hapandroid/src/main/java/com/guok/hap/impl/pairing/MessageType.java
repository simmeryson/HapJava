package com.guok.hap.impl.pairing;

/**
 * TLV Values
 */
public enum MessageType {

	METHOD(0),//kTLVType_Method
	IDENTIFIER(1),//kTLVType_Identifier
	SALT(2),//kTLVType_Salt
	PUBLIC_KEY(3),//kTLVType_PublicKey
	PROOF(4),//kTLVType_Proof
	ENCRYPTED_DATA(5),//kTLVType_EncryptedData
	STATE(6),//kTLVType_State
	ERROR(7),//kTLVType_Error
	RETRY_DELAY(8),//kTLVType_RetryDelay
	CERTIFICATE(9),//kTLVType_Certificate
	SIGNATURE(10),//kTLVType_Signature
	PERMISSIONS(11),//kTLVType_Permissions
	FRAGMENT_DATA(12),//kTLVType_FragmentData
	FRAGMENT_LAST(13),//kTLVType_FragmentLast
	SEPARATOR(255)//kTLVType_Separator
	;
	
	private final short key;
	
	MessageType(short key) {
		this.key = key;
	}
	
	MessageType(int key) {
		this.key = (short) key;
	}
	
	public short getKey() {
		return key;
	}
}
