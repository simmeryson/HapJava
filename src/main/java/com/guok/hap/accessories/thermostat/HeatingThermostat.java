package com.guok.hap.accessories.thermostat;

import com.guok.hap.AccessoryDisplayInfo;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.impl.characteristics.thermostat.HeatingThresholdTemperatureCharacteristic;
import com.guok.hap.impl.services.ThermostatService;

public class HeatingThermostat extends BasicThermostat {

    public HeatingThermostat(int ID, AccessoryDisplayInfo displayInfo) {
        super(ID, displayInfo);
        getSpecificService(ThermostatService.class).addOptionalCharacteristic(new HeatingThresholdTemperatureCharacteristic());
    }

    public HeatingThermostat(int ID, String label) {
        super(ID, label);
        getSpecificService(ThermostatService.class).addOptionalCharacteristic(new HeatingThresholdTemperatureCharacteristic());
    }

    public HeatingThermostat(int ID, AccessoryDisplayInfo displayInfo, String serviceName) {
        super(ID, displayInfo, serviceName);
        getSpecificService(ThermostatService.class).addOptionalCharacteristic(new HeatingThresholdTemperatureCharacteristic());
    }

    public HeatingThermostat(int ID, String label, String serviceName) {
        super(ID, label, serviceName);
        getSpecificService(ThermostatService.class).addOptionalCharacteristic(new HeatingThresholdTemperatureCharacteristic());
    }

    public HeatingThermostat setHeatingThresholdTemperatureCallback(CharacteristicCallBack<Double> callback) {
        getSpecificService(ThermostatService.class).getSpecificCharact(HeatingThresholdTemperatureCharacteristic.class).setCallBack(callback);
        return this;
    }
}
