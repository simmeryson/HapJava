package com.haierubic.os.homekitdemo.hapmaters;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.BooleanCharacteristic;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.EventableCharacteristic;

/**
 * @author guok
 */

public class OnCharact extends BooleanCharacteristic implements EventableCharacteristic {

    public OnCharact() {
        this(null);
    }

    public OnCharact(CharacteristicCallBack<Boolean> callBack) {
        super("00000025-0000-1000-8000-0026BB765291",
                true,
                true,
                "Turn on and off");

        if (callBack != null)
            setCallBack(callBack);
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
    public ListenableFuture<Boolean> getValue() {
        if (this.mCallBack != null)
            return this.mCallBack.getValueCallback(this, this.subcribeCallback != null, new CharacteristicCallBack.FetchCallBack<Boolean>() {
                @Override
                public void fetchValue(Boolean val) {
                    value = val;
                    if (subcribeCallback != null)
                        subcribeCallback.changed();
                }
            });
        return Futures.immediateFuture(value);
    }
}
