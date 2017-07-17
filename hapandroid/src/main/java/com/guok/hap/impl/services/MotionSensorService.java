package com.guok.hap.impl.services;

import com.guok.hap.impl.characteristics.motionsensor.MotionDetectedStateCharacteristic;

public class MotionSensorService extends BaseService {

    public MotionSensorService() {
        this(null);
    }

    public MotionSensorService(String serviceName) {
        super("00000085-0000-1000-8000-0026BB765291", serviceName);

        addCharacteristic(new MotionDetectedStateCharacteristic());
    }
}
