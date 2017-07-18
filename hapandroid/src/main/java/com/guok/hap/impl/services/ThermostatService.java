package com.guok.hap.impl.services;

import com.guok.hap.impl.characteristics.thermostat.CurrentHeatingCoolingModeCharacteristic;
import com.guok.hap.impl.characteristics.thermostat.CurrentTemperatureCharacteristic;
import com.guok.hap.impl.characteristics.thermostat.TargetHeatingCoolingModeCharacteristic;
import com.guok.hap.impl.characteristics.thermostat.TargetTemperatureCharacteristic;
import com.guok.hap.impl.characteristics.thermostat.TemperatureUnitsCharacteristic;

public class ThermostatService extends BaseService {

    public static final String UUID = "0000004A-0000-1000-8000-0026BB765291";

    public ThermostatService() {
        this(null);
    }

    public ThermostatService(String serviceName) {
        super(UUID, serviceName);

        addCharacteristic(new CurrentHeatingCoolingModeCharacteristic());
        addCharacteristic(new CurrentTemperatureCharacteristic());
        addCharacteristic(new TargetHeatingCoolingModeCharacteristic());
        addCharacteristic(new TargetTemperatureCharacteristic());
        addCharacteristic(new TemperatureUnitsCharacteristic());
    }

}
