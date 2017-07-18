package com.guok.hap.impl.services;

import com.guok.hap.impl.characteristics.common.OnCharacteristic;

public class SwitchService extends BaseService {

    public static final String UUID = "00000049-0000-1000-8000-0026BB765291";

    public SwitchService() {
        this(null);
    }

    public SwitchService(String serviceName) {
        super(UUID, serviceName);

        addCharacteristic(new OnCharacteristic());
    }

}
