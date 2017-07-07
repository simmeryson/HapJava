package com.guok.hap.impl.pairing;

/**
 * Created by guokai.
 */
public enum PairMethods {
    RESERVED(0),
    PAIR_SETUP(1),
    PAIR_VERIFY(2),
    ADD_PAIRING(3),
    REMOVE_PAIRING(4),
    LIST_PAIRINGS(5);


    private final int key;

    PairMethods(int key) {
        this.key = key;
    }


    public int getKey() {
        return key;
    }
}
