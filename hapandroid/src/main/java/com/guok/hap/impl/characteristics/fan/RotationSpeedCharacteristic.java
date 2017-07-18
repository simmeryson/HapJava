package com.guok.hap.impl.characteristics.fan;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.CharacteristicUnits;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.characteristics.IntegerCharacteristic;

public class RotationSpeedCharacteristic extends IntegerCharacteristic implements EventableCharacteristic {

    public static final String UUID = "00000029-0000-1000-8000-0026BB765291";

    public RotationSpeedCharacteristic() {
        this(null);
    }

    public RotationSpeedCharacteristic(CharacteristicCallBack<Integer> callBack) {
        super(UUID, true, true, "Rotation speed", 0,
                100, CharacteristicUnits.percentage);

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
