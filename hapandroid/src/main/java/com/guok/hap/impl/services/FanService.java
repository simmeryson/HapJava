package com.guok.hap.impl.services;

import com.guok.hap.impl.characteristics.common.OnCharacteristic;

/**
 * @author guok
 */

public class FanService extends BaseService {

    public static final String UUID = "00000040-0000-1000-8000-0026BB765291";

    public FanService() {
        this(null);
    }

    public FanService(String serviceName) {
        super(UUID, serviceName);
        addCharacteristic(new OnCharacteristic());
    }
}
