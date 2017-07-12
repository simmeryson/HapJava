package com.haierubic.os.homekitdemo.hapmaters;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.EnumCharacteristic;
import com.guok.hap.characteristics.EventableCharacteristic;

/**
 * the state of the position of accessories. This characteristic can be used with doors,
 * windows, awnings, or window coverings.
 *
 * <P> Valid Values
 * <p> 0 Going to minimum
 * <p> 1 Going to maximum
 * <p> 2 Stopped
 * @author guok
 */

public class PositionStateCharact extends EnumCharacteristic implements EventableCharacteristic {

    public PositionStateCharact() {
        this(null);
    }

    public PositionStateCharact(CharacteristicCallBack<Integer> callBack) {
        super("00000072-0000-1000-8000-0026BB765291", false, true, "The position state", 2);

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
