package com.guok.hap.impl.characteristics.media;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.CharacteristicUnits;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.characteristics.IntegerCharacteristic;
import com.guok.hap.impl.responses.HapStatusCodes;

/**
 * @author guok
 */

public class VolumeCharacteristic extends IntegerCharacteristic implements EventableCharacteristic {

    public VolumeCharacteristic() {
        this(null);
    }


    public VolumeCharacteristic(CharacteristicCallBack<Integer> callBack) {
        super("00000119-0000-1000-8000-0026BB765291",
                true, true, "volume of audio", 0, 100, CharacteristicUnits.percentage);
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
    protected int setValue(Integer value) throws Exception {
        this.value = value;

        if (this.subcribeCallback != null)
            this.subcribeCallback.changed();

        if (this.mCallBack != null)
            return this.mCallBack.setValueCallback(value, this.subcribeCallback != null);
        return HapStatusCodes.SUCCESS;
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
