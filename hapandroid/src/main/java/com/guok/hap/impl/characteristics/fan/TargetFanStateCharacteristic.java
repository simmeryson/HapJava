package com.guok.hap.impl.characteristics.fan;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.EnumCharacteristic;
import com.guok.hap.characteristics.EventableCharacteristic;

/**
 * @author guok
 */

public class TargetFanStateCharacteristic extends EnumCharacteristic implements EventableCharacteristic{

    public static final String UUID = "000000BF-0000-1000-8000-0026BB765291";

    public TargetFanStateCharacteristic() {
        this(null);
    }
    public TargetFanStateCharacteristic(CharacteristicCallBack<Integer> callBack) {
        super(UUID, true, true, "target state of a fan", 1);

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
