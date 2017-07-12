package com.guok.hapandroid.hapmaters;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.CharacteristicUnits;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.characteristics.IntegerCharacteristic;

/**
 * @author guok
 */

public class CurrentPositionCharact extends IntegerCharacteristic implements EventableCharacteristic {

    public CurrentPositionCharact() {
        this(null);
    }

    public CurrentPositionCharact(CharacteristicCallBack<Integer> callBack) {
        super("0000006D-0000-1000-8000-0026BB765291", false, true, "The current position", 0, 100, CharacteristicUnits.percentage);

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
