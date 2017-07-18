package com.guok.hap.impl.characteristics.fan;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.EnumCharacteristic;
import com.guok.hap.characteristics.EventableCharacteristic;

public class RotationDirectionCharacteristic extends EnumCharacteristic implements EventableCharacteristic {

    public static final String UUID = "00000028-0000-1000-8000-0026BB765291";

    public RotationDirectionCharacteristic() {
        this(null);
    }

    public RotationDirectionCharacteristic(CharacteristicCallBack<Integer> callBack) {
        super(UUID, true, true, "Rotation Direction", 1);

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
