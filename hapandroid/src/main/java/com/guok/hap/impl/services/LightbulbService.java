package com.guok.hap.impl.services;

import com.guok.hap.impl.characteristics.common.OnCharact;

/**
 * @author guok
 */

public class LightbulbService extends BaseService {

    public LightbulbService() {
        this(null);
    }

    public LightbulbService(String serviceName) {
        super("00000043-0000-1000-8000-0026BB765291", serviceName);

        addCharacteristic(new OnCharact());
    }
}
