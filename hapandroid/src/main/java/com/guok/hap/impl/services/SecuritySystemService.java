package com.guok.hap.impl.services;

import com.guok.hap.impl.characteristics.securitysystem.CurrentSecuritySystemStateCharacteristic;
import com.guok.hap.impl.characteristics.securitysystem.TargetSecuritySystemStateCharacteristic;

public class SecuritySystemService extends BaseService {

    public static final String UUID = "0000007E-0000-1000-8000-0026BB765291";

    public SecuritySystemService() {
        this(null);
    }

    public SecuritySystemService(String serviceName) {
        super(UUID, serviceName);

        addCharacteristic(new CurrentSecuritySystemStateCharacteristic());
        addCharacteristic(new TargetSecuritySystemStateCharacteristic());
    }
}
