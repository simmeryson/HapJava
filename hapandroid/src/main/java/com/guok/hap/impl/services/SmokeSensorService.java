package com.guok.hap.impl.services;

import com.guok.hap.impl.characteristics.smokesensor.SmokeDetectedCharacteristic;

public class SmokeSensorService extends BaseService {

    public SmokeSensorService() {
        this(null);
    }

    public SmokeSensorService(String serviceName) {
        super("00000087-0000-1000-8000-0026BB765291", serviceName);

        addCharacteristic(new SmokeDetectedCharacteristic());
    }
}
