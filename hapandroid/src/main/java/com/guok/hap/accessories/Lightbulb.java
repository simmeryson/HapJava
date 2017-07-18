package com.guok.hap.accessories;

import com.guok.hap.AccessoryDisplayInfo;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.impl.accessories.BaseAccessory;
import com.guok.hap.impl.characteristics.common.OnCharacteristic;
import com.guok.hap.impl.services.LightbulbService;

/**
 * A simple light with a binary state.
 *
 * @author guokai
 */
public class Lightbulb extends BaseAccessory {

    public Lightbulb(int ID, String label) {
        super(ID, label, new LightbulbService());
    }

    public Lightbulb(int ID, String label, String serviceName) {
        super(ID, label, new LightbulbService(serviceName));
    }

    public Lightbulb(int ID, AccessoryDisplayInfo displayInfo, String serviceName) {
        super(ID, displayInfo, new LightbulbService(serviceName));
    }

    public Lightbulb(int ID, AccessoryDisplayInfo displayInfo) {
        super(ID, displayInfo, new LightbulbService());
    }

    public Lightbulb setOnCallback(CharacteristicCallBack<Boolean> onCallbask) {
        getSpecificService(LightbulbService.class).
                getSpecificCharact(OnCharacteristic.class).setCallBack(onCallbask);
        return this;
    }
}
