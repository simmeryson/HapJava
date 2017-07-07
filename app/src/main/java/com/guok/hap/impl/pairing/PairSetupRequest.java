package com.guok.hap.impl.pairing;

import com.guok.hap.impl.pairing.TypeLengthValueUtils.DecodeResult;

import java.math.BigInteger;

abstract class PairSetupRequest {

    /**
     * Pair Setup steps index. Accessory need to handle M1,M3,M5.
     */
    private final static short VALUE_STAGE_1 = 1;
    private final static short VALUE_STAGE_2 = 3;
    private final static short VALUE_STAGE_3 = 5;

    public static PairSetupRequest of(byte[] content) throws Exception {
        DecodeResult d = TypeLengthValueUtils.decode(content);
        short stage = d.getByte(MessageType.STATE);
        switch (stage) {
            case VALUE_STAGE_1:
                return new Stage1Request();

            case VALUE_STAGE_2:
                return new Stage2Request(d);

            case VALUE_STAGE_3:
                return new Stage3Request(d);

            default:
                throw new Exception("Unknown pair process stage: " + stage);
        }
    }

    public abstract Stage getStage();

    public static class Stage1Request extends PairSetupRequest {
        @Override
        public Stage getStage() {
            return Stage.ONE;
        }
    }

    /**
     * iOS device Pair Setup M3 request. with following TLV items
     *
     * <ul>
     * <li> kTLVType_State          (M3)
     * <li> kTLVType_PublicKey      (iOS device's SRP public key)
     * <li> kTLVType_Proof          (iOS device's SRP proof)
     * </ul>
     */
    public static class Stage2Request extends PairSetupRequest {

        private final BigInteger publicKey;//kTLVType_PublicKey
        private final BigInteger proof;//kTLVType_Proof

        public Stage2Request(DecodeResult d) {
            publicKey = d.getBigInt(MessageType.PUBLIC_KEY);
            proof = d.getBigInt(MessageType.PROOF);
        }

        public BigInteger getPublicKey() {
            return publicKey;
        }

        public BigInteger getProof() {
            return proof;
        }

        @Override
        public Stage getStage() {
            return Stage.TWO;
        }

    }

    /**
     * iOS device Pair Setup M5 request. with following TLV items
     * <ul>
     * <li> kTLVType_State          (M5)
     * <li> kTLVType_EncryptedData      (encryptedData with authTag appended)
     * </ul>
     */
    static class Stage3Request extends PairSetupRequest {

        private final byte[] messageData;
        private final byte[] authTagData;

        public Stage3Request(DecodeResult d) {
            messageData = new byte[d.getLength(MessageType.ENCRYPTED_DATA) - 16];
            authTagData = new byte[16];
            d.getBytes(MessageType.ENCRYPTED_DATA, messageData, 0);
            d.getBytes(MessageType.ENCRYPTED_DATA, authTagData, messageData.length);
        }

        public byte[] getMessageData() {
            return messageData;
        }

        public byte[] getAuthTagData() {
            return authTagData;
        }

        @Override
        public Stage getStage() {
            return Stage.THREE;
        }

    }

}
