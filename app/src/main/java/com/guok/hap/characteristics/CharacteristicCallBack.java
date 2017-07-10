package com.guok.hap.characteristics;

import com.google.common.util.concurrent.ListenableFuture;

/**
 * @author guok
 */

public interface CharacteristicCallBack<T> {
    int setValueCallback(T value);
    ListenableFuture<T> getValueCallback(BaseCharacteristic<T> characteristic, FetchCallBack<T> callBack);

    interface FetchCallBack<T>{
         void fetchValue(T val);
    }
}
