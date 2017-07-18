package com.guok.hap.accessories;

import com.guok.hap.AccessoryDisplayInfo;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.impl.accessories.BaseAccessory;
import com.guok.hap.impl.characteristics.common.OnCharacteristic;
import com.guok.hap.impl.services.SwitchService;

/**
 * A simple switch with a binary state.
 *
 * @author guokai
 */
public class Switch extends BaseAccessory {

    public Switch(int ID, AccessoryDisplayInfo displayInfo) {
        super(ID, displayInfo, new SwitchService());
    }

    public Switch(int ID, String label) {
        super(ID, label, new SwitchService());
    }

    public Switch(int ID, AccessoryDisplayInfo displayInfo, String serviceName) {
        super(ID, displayInfo, new SwitchService(serviceName));
    }

    public Switch(int ID, String label, String serviceName) {
        super(ID, label, new SwitchService(serviceName));
    }

    public Switch setOnCallback(CharacteristicCallBack<Boolean> callbask) {
        getSpecificService(SwitchService.class).
                getSpecificCharact(OnCharacteristic.class).setCallBack(callbask);
        return this;
    }
}
