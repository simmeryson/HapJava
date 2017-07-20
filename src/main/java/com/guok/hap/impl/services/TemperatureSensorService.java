package com.guok.hap.impl.services;

import com.guok.hap.impl.characteristics.thermostat.CurrentTemperatureCharacteristic;

public class TemperatureSensorService extends BaseService {

    public static final String UUID = "0000008A-0000-1000-8000-0026BB765291";

    public TemperatureSensorService() {
        this(null);
    }

    public TemperatureSensorService(String serviceName) {
        super(UUID, serviceName);

        addCharacteristic(new CurrentTemperatureCharacteristic());
    }

}
