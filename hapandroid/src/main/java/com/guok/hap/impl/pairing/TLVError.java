package com.guok.hap.impl.pairing;

/**
 * Created by guokai on 28/06/2017.
 */
public enum TLVError {

    UNKNOWN(1),         //kTLVError_Unknown
    AUTHENTICATION(2),  //kTLVError_Authentication
    BACK_OFF(3),        //kTLVError_Backoff
    MAX_PEERS(4),       //kTLVError_MaxPeers
    MAX_RETRIES(5),     //kTLVError_MaxRetries
    UNAVAILABLE(6),     //kTLVError_Unavailable
    BUSY(7)             //kTLVError_Busy
    ;
    private final short key;

    TLVError(short key) {
        this.key = key;
    }

    TLVError(int key) {
        this.key = (short) key;
    }

    public short getKey() {
        return key;
    }
}
