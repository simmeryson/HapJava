package com.guok.hap.impl.characteristics.thermostat;

import com.guok.hap.characteristics.CharacteristicCallBack;

public class TargetHeatingCoolingModeCharacteristic extends
        AbstractHeatingCoolingModeCharacteristic {

//	private final BasicThermostat thermostat;

    public TargetHeatingCoolingModeCharacteristic() {
        this(null);
    }

    public TargetHeatingCoolingModeCharacteristic(CharacteristicCallBack<Integer> callBack) {
        super("00000033-0000-1000-8000-0026BB765291", true, "Target Mode");

        this.mCallBack = callBack;
    }
}
