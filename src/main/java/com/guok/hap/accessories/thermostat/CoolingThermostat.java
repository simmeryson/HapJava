package com.guok.hap.accessories.thermostat;

import com.guok.hap.AccessoryDisplayInfo;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.impl.characteristics.thermostat.CoolingThresholdTemperatureCharact;
import com.guok.hap.impl.services.ThermostatService;

public class CoolingThermostat extends BasicThermostat {

    public CoolingThermostat(int ID, AccessoryDisplayInfo displayInfo) {
        super(ID, displayInfo);
        getSpecificService(ThermostatService.class).addOptionalCharacteristic(new CoolingThresholdTemperatureCharact());
    }

    public CoolingThermostat(int ID, String label) {
        super(ID, label);
        getSpecificService(ThermostatService.class).addOptionalCharacteristic(new CoolingThresholdTemperatureCharact());
    }

    public CoolingThermostat(int ID, AccessoryDisplayInfo displayInfo, String serviceName) {
        super(ID, displayInfo, serviceName);
        getSpecificService(ThermostatService.class).addOptionalCharacteristic(new CoolingThresholdTemperatureCharact());
    }

    public CoolingThermostat(int ID, String label, String serviceName) {
        super(ID, label, serviceName);
        getSpecificService(ThermostatService.class).addOptionalCharacteristic(new CoolingThresholdTemperatureCharact());
    }

    public CoolingThermostat setCurrentTemperatureCallback(CharacteristicCallBack<Double> callback) {
        getSpecificService(ThermostatService.class).getSpecificCharact(CoolingThresholdTemperatureCharact.class).setCallBack(callback);
        return this;
    }
}
