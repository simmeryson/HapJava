package com.guok.hap.impl.characteristics.common;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.EnumCharacteristic;
import com.guok.hap.characteristics.EventableCharacteristic;

/**
 * Describing the service is active or not. 0 means inactive. 1 means active.
 *
 * @author guok
 */

public class ActiveCharacteristic extends EnumCharacteristic implements EventableCharacteristic {

    public static final String UUID = "000000B0-0000-1000-8000-0026BB765291";

    public ActiveCharacteristic() {
        this(null);
    }

    public ActiveCharacteristic(CharacteristicCallBack<Integer> callBack) {
        super(UUID, true, true, "if the service current active", 1);

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

}
