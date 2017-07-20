package com.guok.hap.impl.characteristics.fan;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.EnumCharacteristic;
import com.guok.hap.characteristics.EventableCharacteristic;

/**
 * @author guok
 */

public class CurrentFanStateCharacteristic extends EnumCharacteristic implements EventableCharacteristic {

    public static final String UUID = "000000AF-0000-1000-8000-0026BB765291";

    public CurrentFanStateCharacteristic() {
        this(null);
    }
    public CurrentFanStateCharacteristic(CharacteristicCallBack<Integer> callBack) {
        super(UUID, false, true, "current state of a fan", 2);

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
