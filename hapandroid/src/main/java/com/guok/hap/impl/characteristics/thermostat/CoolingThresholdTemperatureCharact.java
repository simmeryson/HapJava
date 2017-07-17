package com.guok.hap.impl.characteristics.thermostat;

import com.guok.hap.characteristics.CharacteristicCallBack;

public class CoolingThresholdTemperatureCharact extends
        AbstractTemperatureCharacteristic {

    public CoolingThresholdTemperatureCharact() {
        this(null);
    }

    public CoolingThresholdTemperatureCharact(CharacteristicCallBack<Double> callBack) {
        super("0000000D-0000-1000-8000-0026BB765291", true, "Temperature above which cooling will be active", 10, 35);

        this.mCallBack = callBack;
    }
}
