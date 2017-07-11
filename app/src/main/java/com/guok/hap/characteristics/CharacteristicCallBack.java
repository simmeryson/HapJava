package com.guok.hap.characteristics;

import com.google.common.util.concurrent.ListenableFuture;

/**
 * @author guok
 */

public interface CharacteristicCallBack<T> {
    int setValueCallback(T value, boolean subscribe);

    ListenableFuture<T> getValueCallback(BaseCharacteristic<T> characteristic, boolean subscribe, FetchCallBack<T> callBack);

    interface FetchCallBack<T> {
        void fetchValue(T val);
    }
}
