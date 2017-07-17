package com.guok.hap.impl.services;

import com.guok.hap.impl.characteristics.common.OnCharact;
import com.guok.hap.impl.characteristics.outlet.OutletInUseCharacteristic;

public class OutletService extends BaseService {

    public OutletService() {
        this(null);
    }

    public OutletService(String serviceName) {
        super("00000047-0000-1000-8000-0026BB765291", serviceName);

        addCharacteristic(new OnCharact());
        addCharacteristic(new OutletInUseCharacteristic());
    }

}
