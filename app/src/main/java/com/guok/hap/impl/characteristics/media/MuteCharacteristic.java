package com.guok.hap.impl.characteristics.media;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.BooleanCharacteristic;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.EventableCharacteristic;

/**
 * 0 means mute is on, or audio is on.
 * 1 means mute is off, or audio is off;
 *
 * @author guok
 */

public class MuteCharacteristic extends BooleanCharacteristic implements EventableCharacteristic {


    public MuteCharacteristic() {
        this(null);
    }

    public MuteCharacteristic(CharacteristicCallBack<Boolean> callBack) {
        super("0000011A-0000-1000-8000-0026BB765291", true, true, "control of audio output");
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
