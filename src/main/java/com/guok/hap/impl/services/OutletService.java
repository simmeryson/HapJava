package com.guok.hap.impl.services;

import com.guok.hap.impl.characteristics.common.OnCharacteristic;
import com.guok.hap.impl.characteristics.outlet.OutletInUseCharacteristic;

public class OutletService extends BaseService {

    public static final String UUID = "00000047-0000-1000-8000-0026BB765291";

    public OutletService() {
        this(null);
    }

    public OutletService(String serviceName) {
        super(UUID, serviceName);

        addCharacteristic(new OnCharacteristic());
        addCharacteristic(new OutletInUseCharacteristic());
    }

}
