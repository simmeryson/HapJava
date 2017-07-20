package com.guok.hap.accessories.thermostat;

import com.guok.hap.AccessoryDisplayInfo;
import com.guok.hap.accessories.TemperatureSensor;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.impl.characteristics.thermostat.CurrentHeatingCoolingModeCharacteristic;
import com.guok.hap.impl.characteristics.thermostat.CurrentTemperatureCharacteristic;
import com.guok.hap.impl.characteristics.thermostat.TargetHeatingCoolingModeCharacteristic;
import com.guok.hap.impl.characteristics.thermostat.TargetTemperatureCharacteristic;
import com.guok.hap.impl.characteristics.thermostat.TemperatureUnitsCharacteristic;
import com.guok.hap.impl.services.ThermostatService;

public class BasicThermostat extends TemperatureSensor {

    private ThermostatService mThermostatService;

    public BasicThermostat(int ID, AccessoryDisplayInfo displayInfo) {
        super(ID, displayInfo);
        mThermostatService = addServices(new ThermostatService());
    }

    public BasicThermostat(int ID, String label) {
        super(ID, label);
        mThermostatService = addServices(new ThermostatService());
    }

    public BasicThermostat(int ID, AccessoryDisplayInfo displayInfo, String serviceName) {
        super(ID, displayInfo, serviceName);
        mThermostatService = addServices(new ThermostatService());
    }

    public BasicThermostat(int ID, String label, String serviceName) {
        super(ID, label, serviceName);
        mThermostatService = addServices(new ThermostatService());
    }


    public BasicThermostat setCurrentHeatingCoolingModeCallback(CharacteristicCallBack<Integer> callback) {
        mThermostatService.getSpecificCharact(CurrentHeatingCoolingModeCharacteristic.class).setCallBack(callback);
        return this;
    }

    public BasicThermostat setCurrentTemperatureCallback(CharacteristicCallBack<Double> callback) {
        mThermostatService.getSpecificCharact(CurrentTemperatureCharacteristic.class).setCallBack(callback);
        return this;
    }

    public BasicThermostat setTargetHeatingCoolingModeCallback(CharacteristicCallBack<Integer> callback) {
        mThermostatService.getSpecificCharact(TargetHeatingCoolingModeCharacteristic.class).setCallBack(callback);
        return this;
    }
    public BasicThermostat setTargetTemperatureCallback(CharacteristicCallBack<Double> callback) {
        mThermostatService.getSpecificCharact(TargetTemperatureCharacteristic.class).setCallBack(callback);
        return this;
    }
    public BasicThermostat setTemperatureUnitsCallback(CharacteristicCallBack<Integer> callback) {
        mThermostatService.getSpecificCharact(TemperatureUnitsCharacteristic.class).setCallBack(callback);
        return this;
    }
}
