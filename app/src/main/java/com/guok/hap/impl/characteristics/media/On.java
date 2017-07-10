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
 * @author guok
 */

public class On extends BooleanCharacteristic implements EventableCharacteristic {

    public On() {
        this(null);
    }

    public On(CharacteristicCallBack<Boolean> callBack) {
        super("00000025-0000-1000-8000-0026BB765291",
                true,
                true,
                "Turn on and off");

        if (callBack != null)
            setCallBack(callBack);
    }

    public On(Consumer<HomekitCharacteristicChangeCallback> subscriber, Runnable unsubscriber, CharacteristicCallBack<Boolean> callBack) {
        this(callBack);
        this.subscriber = subscriber;
        this.unsubscriber = unsubscriber;
    }

    @Override
    public void subscribe(HomekitCharacteristicChangeCallback callback) {
        if (subscriber != null)
            subscriber.accept(callback);
    }

    @Override
    public void unsubscribe() {
        if (unsubscriber != null) {
            unsubscriber.run();
        }
    }

    @Override
    protected int setValue(Boolean value) throws Exception {
        this.value = value;
        System.out.println("On mCallBack  :" + this.mCallBack);
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
