package com.guok.hap.impl.characteristics.thermostat;

import com.guok.hap.characteristics.CharacteristicCallBack;

public class TargetHeatingCoolingModeCharacteristic extends
        AbstractHeatingCoolingModeCharacteristic {

    //	private final BasicThermostat thermostat;
    public static final String UUID = "00000033-0000-1000-8000-0026BB765291";

    public TargetHeatingCoolingModeCharacteristic() {
        this(null);
    }

    public TargetHeatingCoolingModeCharacteristic(CharacteristicCallBack<Integer> callBack) {
        super(UUID, true, "Target Mode");

        this.mCallBack = callBack;
    }
}
