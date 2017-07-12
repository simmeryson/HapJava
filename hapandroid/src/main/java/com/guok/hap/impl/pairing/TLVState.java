package com.guok.hap.impl.pairing;

/**
 * @author guok
 */

public enum TLVState {

    M1(1),
    M2(2),
    M3(3),
    M4(4),
    M5(5),
    M6(6),
    ;

    private final short key;

    TLVState(short key) {
        this.key = key;
    }

    TLVState(int key) {
        this.key = (short) key;
    }

    public short getKey() {
        return key;
    }
}
