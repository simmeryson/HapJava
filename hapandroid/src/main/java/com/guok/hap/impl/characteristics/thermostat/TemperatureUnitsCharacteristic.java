package com.guok.hap.impl.characteristics.thermostat;

import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.EnumCharacteristic;
import com.guok.hap.impl.responses.HapStatusCodes;


public class TemperatureUnitsCharacteristic extends EnumCharacteristic {


    public TemperatureUnitsCharacteristic() {
        this(null);
    }

    public TemperatureUnitsCharacteristic(CharacteristicCallBack<Integer> callBack) {
        super("00000036-0000-1000-8000-0026BB765291", false, true, "The temperature unit", 1);

        this.mCallBack = callBack;
    }

    @Override
    protected int setValue(Integer value) throws Exception {
        //Not writable
        return HapStatusCodes.READ_OLNY;
    }

}
