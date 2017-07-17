package com.guok.hap.impl.services;

import com.guok.hap.impl.characteristics.common.ActiveCharacteristic;

/**
 * This service requires iOS 10.3
 *
 * @author guok
 */

public class FanV2Service extends BaseService {

    public FanV2Service() {
        this(null);
    }

    public FanV2Service(String serviceName) {
        super("000000B7-0000-1000-8000-0026BB765291", serviceName);

        addCharacteristic(new ActiveCharacteristic());
    }
}
