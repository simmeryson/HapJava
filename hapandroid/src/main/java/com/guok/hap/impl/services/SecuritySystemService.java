package com.guok.hap.impl.services;

import com.guok.hap.accessories.SecuritySystem;
import com.guok.hap.impl.characteristics.securitysystem.CurrentSecuritySystemStateCharacteristic;
import com.guok.hap.impl.characteristics.securitysystem.SecuritySystemAlarmTypeCharacteristic;
import com.guok.hap.impl.characteristics.securitysystem.TargetSecuritySystemStateCharacteristic;

public class SecuritySystemService extends AbstractServiceImpl {

    public SecuritySystemService(SecuritySystem securitySystem) {
        this(securitySystem, securitySystem.getLabel());
    }

    public SecuritySystemService(SecuritySystem securitySystem, String serviceName) {
        super("0000007E-0000-1000-8000-0026BB765291", securitySystem, serviceName);
        addCharacteristic(new CurrentSecuritySystemStateCharacteristic(securitySystem));
        addCharacteristic(new TargetSecuritySystemStateCharacteristic(securitySystem));
        addCharacteristic(new SecuritySystemAlarmTypeCharacteristic(securitySystem));
    }
}
