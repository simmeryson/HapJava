package com.guok.hap.impl.services;

import com.guok.hap.impl.characteristics.common.OnCharact;

/**
 * @author guok
 */

public class FanService extends BaseService {
    public FanService() {
        this(null);
    }

    public FanService(String serviceName) {
        super("00000040-0000-1000-8000-0026BB765291", serviceName);
        addCharacteristic(new OnCharact());
    }
}
