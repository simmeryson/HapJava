package com.guok.hap.impl.services;

import com.guok.hap.impl.characteristics.lock.mechanism.LockCurrentStateCharacteristic;
import com.guok.hap.impl.characteristics.lock.mechanism.LockTargetStateCharacteristic;

public class LockMechanismService extends BaseService {

    public static final String UUID = "00000045-0000-1000-8000-0026BB765291";

    public LockMechanismService() {
        this(null);
    }

    public LockMechanismService(String serviceName) {
        super(UUID, serviceName);

        addCharacteristic(new LockCurrentStateCharacteristic());
        addCharacteristic(new LockTargetStateCharacteristic());
    }
}
