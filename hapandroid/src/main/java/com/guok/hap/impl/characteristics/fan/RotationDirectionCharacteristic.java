package com.guok.hap.impl.characteristics.fan;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.EnumCharacteristic;
import com.guok.hap.characteristics.EventableCharacteristic;

public class RotationDirectionCharacteristic extends EnumCharacteristic implements EventableCharacteristic {

//	private final Fan fan;

    public RotationDirectionCharacteristic() {
        this(null);
    }

    public RotationDirectionCharacteristic(CharacteristicCallBack<Integer> callBack) {
        super("00000028-0000-1000-8000-0026BB765291", true, true, "Rotation Direction", 1);

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
