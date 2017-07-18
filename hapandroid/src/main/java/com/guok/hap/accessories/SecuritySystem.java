package com.guok.hap.accessories;

import com.guok.hap.AccessoryDisplayInfo;
import com.guok.hap.accessories.properties.TargetSecuritySystemState;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.impl.accessories.BaseAccessory;
import com.guok.hap.impl.characteristics.securitysystem.CurrentSecuritySystemStateCharacteristic;
import com.guok.hap.impl.characteristics.securitysystem.TargetSecuritySystemStateCharacteristic;
import com.guok.hap.impl.services.SecuritySystemService;

/**
 * <p>A security system that can be armed so that when a contact sensor is opened or a motion
 * sensor detects movement, then a siren could be fired off. There are different modes for arming
 * the system. See {@link TargetSecuritySystemState} for more information.</p>
 *
 * @author guokai
 */
public class SecuritySystem extends BaseAccessory {

    public SecuritySystem(int ID, AccessoryDisplayInfo displayInfo) {
        super(ID, displayInfo, new SecuritySystemService());
    }

    public SecuritySystem(int ID, String label) {
        super(ID, label, new SecuritySystemService());
    }

    public SecuritySystem(int ID, AccessoryDisplayInfo displayInfo, String serviceName) {
        super(ID, displayInfo, new SecuritySystemService(serviceName));
    }

    public SecuritySystem(int ID, String label, String serviceName) {
        super(ID, label, new SecuritySystemService(serviceName));
    }

    public SecuritySystem setCurrentSecuritySystemStateCallback(CharacteristicCallBack<Integer> callbask) {
        getSpecificService(SecuritySystemService.class).
                getSpecificCharact(CurrentSecuritySystemStateCharacteristic.class).setCallBack(callbask);
        return this;
    }


    public SecuritySystem setTargetSecuritySystemStateCallback(CharacteristicCallBack<Integer> callbask) {
        getSpecificService(SecuritySystemService.class).
                getSpecificCharact(TargetSecuritySystemStateCharacteristic.class).setCallBack(callbask);
        return this;
    }
}