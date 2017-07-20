package com.guok.hap.impl.services;

import com.guok.hap.impl.characteristics.common.OnCharacteristic;

/**
 * @author guok
 */

public class LightbulbService extends BaseService {

    public static final String UUID = "00000043-0000-1000-8000-0026BB765291";

    public LightbulbService() {
        this(null);
    }

    public LightbulbService(String serviceName) {
        super(UUID, serviceName);

        addCharacteristic(new OnCharacteristic());
    }
}
