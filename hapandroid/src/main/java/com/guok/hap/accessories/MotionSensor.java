package com.guok.hap.accessories;

import com.guok.hap.AccessoryDisplayInfo;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.impl.accessories.BaseAccessory;
import com.guok.hap.impl.characteristics.motionsensor.MotionDetectedStateCharacteristic;
import com.guok.hap.impl.services.MotionSensorService;

/**
 * <p>A motion sensor that reports whether motion has been detected.</p>
 * <p>
 * <p>Motion sensors that run on batteries will need to implement this interface
 * and also implement {@link BatteryAccessory}.</p>
 *
 * @author guokai
 */
public class MotionSensor extends BaseAccessory {

    public MotionSensor(int ID, AccessoryDisplayInfo displayInfo) {
        super(ID, displayInfo, new MotionSensorService());
    }

    public MotionSensor(int ID, String label) {
        super(ID, label, new MotionSensorService());
    }


    public MotionSensor(int ID, AccessoryDisplayInfo displayInfo, String serviceName) {
        super(ID, displayInfo, new MotionSensorService(serviceName));
    }

    public MotionSensor(int ID, String label, String serviceName) {
        super(ID, label, new MotionSensorService(serviceName));
    }


    public MotionSensor setMotionDetectedStateCallback(CharacteristicCallBack<Boolean> callbask) {
        getSpecificService(MotionSensorService.class).
                getSpecificCharact(MotionDetectedStateCharacteristic.class).setCallBack(callbask);
        return this;
    }
}
