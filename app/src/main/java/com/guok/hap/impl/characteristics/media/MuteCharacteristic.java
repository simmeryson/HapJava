package com.guok.hap.impl.characteristics.media;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.BooleanCharacteristic;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.impl.Consumer;
import com.guok.hap.impl.responses.HapStatusCodes;

/**
 * 0 means mute is on, or audio is on.
 * 1 means mute is off, or audio is off;
 *
 * @author guok
 */

public class MuteCharacteristic extends BooleanCharacteristic implements EventableCharacteristic {


    public MuteCharacteristic() {
        this(null, null);
    }

    public MuteCharacteristic(Consumer<HomekitCharacteristicChangeCallback> subscriber, Runnable unsubscriber, CharacteristicCallBack<Boolean> callBack) {
        super("0000011A-0000-1000-8000-0026BB765291", true, true, "control of audio output");
        this.subscriber = subscriber;
        this.unsubscriber = unsubscriber;
        this.mCallBack = callBack;
        value = false;
    }

    public MuteCharacteristic(Consumer<HomekitCharacteristicChangeCallback> subscriber, Runnable unsubscriber) {
        this(subscriber, unsubscriber, null);
    }

    @Override
    public void subscribe(HomekitCharacteristicChangeCallback callback) {
        if (subscriber != null)
            subscriber.accept(callback);
    }

    @Override
    public void unsubscribe() {
        if (unsubscriber != null)
            unsubscriber.run();
    }

    @Override
    protected int setValue(Boolean value) throws Exception {
        this.value = value;
        System.out.println("MuteCharacteristic mCallBack  :" + this.mCallBack);
        if (this.mCallBack != null)
            return this.mCallBack.setValueCallback(value);
        return HapStatusCodes.SUCCESS;
    }

    @Override
    public ListenableFuture<Boolean> getValue() {
        if (this.mCallBack != null)
            return this.mCallBack.getValueCallback(this, new CharacteristicCallBack.FetchCallBack<Boolean>() {
                @Override
                public void fetchValue(Boolean val) {
                    value = val;
                }
            });
        return Futures.immediateFuture(value);
    }
}
