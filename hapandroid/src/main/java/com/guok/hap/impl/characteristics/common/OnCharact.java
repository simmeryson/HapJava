package com.guok.hap.impl.characteristics.common;

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

}
