package com.guok.hapandroid.hapmaters;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.EnumCharacteristic;
import com.guok.hap.characteristics.EventableCharacteristic;

/**
 * Describing the service is active or not. 0 means inactive. 1 means active.
 *
 * @author guok
 */

public class ActiveCharact extends EnumCharacteristic implements EventableCharacteristic {

    public ActiveCharact() {
        this(null);
    }
    public ActiveCharact(CharacteristicCallBack<Integer> callBack) {
        super("000000B0-0000-1000-8000-0026BB765291", true, true, "if the service current active", 1);

        this.mCallBack = callBack;
    }


    @Override
    public void subscribe(HomekitCharacteristicChangeCallback callback) {
        this.subcribeCallback = callback;
    }

    @Override
    public void unsubscribe() {
        this.subcribeCallback = null;
    }

    @Override
    public ListenableFuture<Integer> getValue() {
        if (this.mCallBack != null)
            return this.mCallBack.getValueCallback(this, this.subcribeCallback != null, new CharacteristicCallBack.FetchCallBack<Integer>() {
                @Override
                public void fetchValue(Integer val) {
                    value = val;
                    if (subcribeCallback != null)
                        subcribeCallback.changed();
                }
            });
        return Futures.immediateFuture(value);
    }
}
