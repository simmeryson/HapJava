package com.guok.hap.accessories;

import com.guok.hap.AccessoryDisplayInfo;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.impl.accessories.BaseAccessory;
import com.guok.hap.impl.characteristics.light.AmbientLightLevelCharacteristic;
import com.guok.hap.impl.services.LightSensorService;

/**
 * A light sensor that reports current ambient light level.
 *
 * @author guokai
 */
public class LightSensor extends BaseAccessory {

    public LightSensor(int ID, AccessoryDisplayInfo displayInfo) {
        super(ID, displayInfo, new LightSensorService());
    }

    public LightSensor(int ID, String label) {
        super(ID, label, new LightSensorService());
    }

    public LightSensor(int ID, AccessoryDisplayInfo displayInfo, String serviceName) {
        super(ID, displayInfo, new LightSensorService(serviceName));
    }

    public LightSensor(int ID, String label, String serviceName) {
        super(ID, label, new LightSensorService(serviceName));
    }

    public LightSensor setAmbientLightLevelCallback(CharacteristicCallBack<Double> callbask) {
        getSpecificService(LightSensorService.class).
                getSpecificCharact(AmbientLightLevelCharacteristic.class).setCallBack(callbask);
        return this;
    }
}
