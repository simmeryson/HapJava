package com.guok.hap.impl.characteristics.thermostat;

import com.guok.hap.characteristics.CharacteristicCallBack;

public class TargetTemperatureCharacteristic extends
        AbstractTemperatureCharacteristic {

    public static final String UUID = "00000035-0000-1000-8000-0026BB765291";

    public TargetTemperatureCharacteristic() {
        this(null);
    }

    public TargetTemperatureCharacteristic(CharacteristicCallBack<Double> callBack) {
        super(UUID, true, "Target Temperature", 10, 38);

        this.mCallBack = callBack;
    }
}
