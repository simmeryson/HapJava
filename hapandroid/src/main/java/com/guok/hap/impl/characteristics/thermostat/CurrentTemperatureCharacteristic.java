package com.guok.hap.impl.characteristics.thermostat;

import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.impl.responses.HapStatusCodes;

public class CurrentTemperatureCharacteristic extends
        AbstractTemperatureCharacteristic {

    public CurrentTemperatureCharacteristic() {
        this(null);
    }

    public CurrentTemperatureCharacteristic(CharacteristicCallBack<Double> callBack) {
        super("00000011-0000-1000-8000-0026BB765291", false, "Current Temperature", 0, 100);

        this.mCallBack = callBack;
    }

    @Override
    protected int setValue(Double value) throws Exception {
        //Not writable
        return HapStatusCodes.READ_OLNY;
    }
}
