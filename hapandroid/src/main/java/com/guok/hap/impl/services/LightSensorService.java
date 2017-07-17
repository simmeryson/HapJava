package com.guok.hap.impl.services;

import com.guok.hap.impl.characteristics.light.AmbientLightLevelCharacteristic;

public class LightSensorService extends BaseService {

    public LightSensorService() {
        this(null);
    }

    public LightSensorService(String serviceName) {
        super("00000084-0000-1000-8000-0026BB765291", serviceName);

        addCharacteristic(new AmbientLightLevelCharacteristic());
    }
}
