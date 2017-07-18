package com.guok.hap.impl.services;

import com.guok.hap.impl.characteristics.motionsensor.MotionDetectedStateCharacteristic;

public class MotionSensorService extends BaseService {

    public static final String UUID = "00000085-0000-1000-8000-0026BB765291";

    public MotionSensorService() {
        this(null);
    }

    public MotionSensorService(String serviceName) {
        super(UUID, serviceName);

        addCharacteristic(new MotionDetectedStateCharacteristic());
    }
}
