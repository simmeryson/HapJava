package com.guok.hapandroid.hapmaters;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.CharacteristicUnits;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.characteristics.FloatCharacteristic;

/**
 * @author guok
 */

public class SaturationCharact extends FloatCharacteristic implements EventableCharacteristic {

    public SaturationCharact() {
        this(null);
    }

    public SaturationCharact(CharacteristicCallBack<Double> callBack) {
        super("0000002F-0000-1000-8000-0026BB765291", true, true, "Adjust saturation of the light", 0,
                100, 1, CharacteristicUnits.percentage);

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
    protected ListenableFuture<Double> getDoubleValue() {
        if (this.mCallBack != null)
            return this.mCallBack.getValueCallback(this, this.subcribeCallback != null, new CharacteristicCallBack.FetchCallBack<Double>() {
                @Override
                public void fetchValue(Double val) {
                    value = val;
                    if (subcribeCallback != null)
                        subcribeCallback.changed();//iOS could receive new value via this method
                }
            });
        return Futures.immediateFuture(value);
    }
}
