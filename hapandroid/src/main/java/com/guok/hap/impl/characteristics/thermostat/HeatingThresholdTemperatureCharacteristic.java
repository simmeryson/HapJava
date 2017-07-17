package com.guok.hap.impl.characteristics.thermostat;

import com.guok.hap.characteristics.CharacteristicCallBack;

public class HeatingThresholdTemperatureCharacteristic extends
        AbstractTemperatureCharacteristic {

    public HeatingThresholdTemperatureCharacteristic() {
        this(null);
    }

    public HeatingThresholdTemperatureCharacteristic(CharacteristicCallBack<Double> callBack) {
        super("00000012-0000-1000-8000-0026BB765291", true, "Temperature below which heating will be active", 0, 25);

        this.mCallBack = callBack;
    }
}
