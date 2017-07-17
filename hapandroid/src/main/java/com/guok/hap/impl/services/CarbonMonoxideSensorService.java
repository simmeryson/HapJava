package com.guok.hap.impl.services;

import com.guok.hap.impl.characteristics.carbonmonoxide.CarbonMonoxideDetectedCharacteristic;

/**
 * @author guok
 */
public class CarbonMonoxideSensorService extends BaseService {

    public CarbonMonoxideSensorService() {
        this(null);
    }

    public CarbonMonoxideSensorService(String serviceName) {
        super("0000007F-0000-1000-8000-0026BB765291", serviceName);

        addCharacteristic(new CarbonMonoxideDetectedCharacteristic());
    }
}
