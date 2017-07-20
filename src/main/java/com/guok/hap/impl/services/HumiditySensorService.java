package com.guok.hap.impl.services;

import com.guok.hap.impl.characteristics.humiditysensor.CurrentRelativeHumidityCharacteristic;

public class HumiditySensorService extends BaseService {

    public static final String UUID = "00000082-0000-1000-8000-0026BB765291";

    public HumiditySensorService() {
        this(null);
    }

    public HumiditySensorService(String serviceName) {
        super(UUID, serviceName);

        addCharacteristic(new CurrentRelativeHumidityCharacteristic());
    }

}
