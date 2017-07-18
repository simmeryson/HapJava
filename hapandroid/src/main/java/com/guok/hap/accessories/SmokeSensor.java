package com.guok.hap.accessories;

import com.guok.hap.AccessoryDisplayInfo;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.impl.accessories.BaseAccessory;
import com.guok.hap.impl.characteristics.smokesensor.SmokeDetectedCharacteristic;
import com.guok.hap.impl.services.SmokeSensorService;

/**
 * <p>A smoke sensor reports whether smoke has been detected or not.</p>
 * <p>
 * <p>Smoke sensors that run on batteries will need to implement this interface
 * and also implement {@link BatteryAccessory}.</p>
 *
 * @author guokai
 */
public class SmokeSensor extends BaseAccessory {

    public SmokeSensor(int ID, AccessoryDisplayInfo displayInfo) {
        super(ID, displayInfo, new SmokeSensorService());
    }

    public SmokeSensor(int ID, String label) {
        super(ID, label, new SmokeSensorService());
    }

    public SmokeSensor(int ID, AccessoryDisplayInfo displayInfo, String serviceName) {
        super(ID, displayInfo, new SmokeSensorService(serviceName));
    }

    public SmokeSensor(int ID, String label, String serviceName) {
        super(ID, label, new SmokeSensorService(serviceName));
    }

    public SmokeSensor setSmokeDetectedCallback(CharacteristicCallBack<Integer> callbask) {
        getSpecificService(SmokeSensorService.class).
                getSpecificCharact(SmokeDetectedCharacteristic.class).setCallBack(callbask);
        return this;
    }
}
