package com.guok.hapandroid.hapmaters;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.guok.hap.characteristics.BooleanCharacteristic;
import com.guok.hap.characteristics.CharacteristicCallBack;

/**
 * @author guok
 */

public class HoldPositionCharact extends BooleanCharacteristic {

    public HoldPositionCharact() {
        this(null);
    }

    public HoldPositionCharact(CharacteristicCallBack<Boolean> callBack) {
        super("0000006F-0000-1000-8000-0026BB765291", true, false, "Whether or not to hold position");

        this.mCallBack = callBack;
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
