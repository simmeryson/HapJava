package com.guok.hap.characteristics;

/**
 * Created by guokai on 29/06/2017.
 */
public enum CharacteristicValueFormats {
    bool,
    uint8,
    uint16,
    uint32,
    uint64,
    INT,
    FLOAT,
    STRING,
    tlv8,
    data;


    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
