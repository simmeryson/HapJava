package com.guok.hap.impl.services;

import com.guok.hap.impl.characteristics.common.OnCharact;

public class SwitchService extends BaseService {

    public SwitchService() {
        this(null);
    }

    public SwitchService(String serviceName) {
        super("00000049-0000-1000-8000-0026BB765291", serviceName);

        addCharacteristic(new OnCharact());
    }

}
