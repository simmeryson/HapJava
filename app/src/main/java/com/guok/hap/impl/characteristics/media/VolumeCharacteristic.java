package com.guok.hap.impl.characteristics.media;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.CharacteristicUnits;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.characteristics.IntegerCharacteristic;
import com.guok.hap.impl.Consumer;
import com.guok.hap.impl.responses.HapStatusCodes;

/**
 * @author guok
 */

public class VolumeCharacteristic extends IntegerCharacteristic implements EventableCharacteristic {

    public VolumeCharacteristic() {
        this(null, null);
    }

    public VolumeCharacteristic(Consumer<HomekitCharacteristicChangeCallback> subscriber, Runnable unsubscriber) {
        this(subscriber, unsubscriber, null);
    }

    public VolumeCharacteristic(Consumer<HomekitCharacteristicChangeCallback> subscriber, Runnable unsubscriber, CharacteristicCallBack<Integer> callBack) {
        super("00000119-0000-1000-8000-0026BB765291",
                true, true, "volume of audio", 0, 100, CharacteristicUnits.percentage);
        this.subscriber = subscriber;
        this.unsubscriber = unsubscriber;
        this.mCallBack = callBack;

        value = 100;
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
    protected int setValue(Integer value) throws Exception {
        this.value = value;
        if (this.mCallBack != null)
            return this.mCallBack.setValueCallback(value);
        return HapStatusCodes.SUCCESS;
    }

    @Override
    public ListenableFuture<Integer> getValue() {
        if (this.mCallBack != null)
            return this.mCallBack.getValueCallback(this, new CharacteristicCallBack.FetchCallBack<Integer>() {
                @Override
                public void fetchValue(Integer val) {
                    value = val;
                }
            });
        return Futures.immediateFuture(value);
    }
}
