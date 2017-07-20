package com.guok.hap.impl.characteristics.thermostat;

import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.impl.responses.HapStatusCodes;

public class CurrentHeatingCoolingModeCharacteristic extends
        AbstractHeatingCoolingModeCharacteristic {

    public static final String UUID = "0000000F-0000-1000-8000-0026BB765291";

    public CurrentHeatingCoolingModeCharacteristic() {
        this(null);
    }

    public CurrentHeatingCoolingModeCharacteristic(CharacteristicCallBack<Integer> callBack) {
        super(UUID, false, "Current Mode");

        this.mCallBack = callBack;
    }

    @Override
    protected int setValue(Integer value) throws Exception {
        //Not writable
        return HapStatusCodes.READ_OLNY;
    }
}
