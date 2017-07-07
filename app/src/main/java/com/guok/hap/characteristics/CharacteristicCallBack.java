package com.guok.hap.characteristics;

/**
 * @author guok
 */

public interface CharacteristicCallBack<T> {
    int setValueCallback(T value);
    int getValueCallback(T value);
}
