package com.guok.hap.accessories;

import com.guok.hap.AccessoryDisplayInfo;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.impl.accessories.BaseAccessory;
import com.guok.hap.impl.characteristics.common.OnCharacteristic;
import com.guok.hap.impl.services.FanService;

/**
 * A fan, with power and rotational characteristics.
 *
 * @author guok
 */
public class Fan extends BaseAccessory {

    public Fan(int ID, String label) {
        super(ID, label, new FanService());
    }

    public Fan(int ID, String label, String serviceName) {
        super(ID, label, new FanService(serviceName));
    }

    public Fan(int ID, AccessoryDisplayInfo displayInfo, String serviceName) {
        super(ID, displayInfo, new FanService(serviceName));
    }

    public Fan(int ID, AccessoryDisplayInfo displayInfo) {
        super(ID, displayInfo, new FanService());
    }

    public Fan setOnCallback(CharacteristicCallBack<Boolean> onCallbask) {
        getSpecificService(FanService.class).
                getSpecificCharact(OnCharacteristic.class).setCallBack(onCallbask);
        return this;
    }
}
