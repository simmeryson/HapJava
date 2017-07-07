package com.guok.hap.characteristics;

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


    /**
     * @author guok
     */
    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
