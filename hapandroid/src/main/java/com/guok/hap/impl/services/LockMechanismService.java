package com.guok.hap.impl.services;

import com.guok.hap.impl.characteristics.lock.mechanism.LockCurrentStateCharacteristic;
import com.guok.hap.impl.characteristics.lock.mechanism.LockTargetStateCharacteristic;

public class LockMechanismService extends BaseService {

    public LockMechanismService() {
        this(null);
    }

    public LockMechanismService(String serviceName) {
        super("00000045-0000-1000-8000-0026BB765291", serviceName);

        addCharacteristic(new LockCurrentStateCharacteristic());
        addCharacteristic(new LockTargetStateCharacteristic());
    }
}
