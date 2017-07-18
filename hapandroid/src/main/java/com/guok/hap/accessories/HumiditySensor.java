package com.guok.hap.accessories;

import com.guok.hap.AccessoryDisplayInfo;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.impl.accessories.BaseAccessory;
import com.guok.hap.impl.characteristics.humiditysensor.CurrentRelativeHumidityCharacteristic;
import com.guok.hap.impl.services.HumiditySensorService;

/**
 * A humidity sensor that reports the current relative humidity.
 *
 * @author guokai
 */
public class HumiditySensor extends BaseAccessory {

    public HumiditySensor(int ID, AccessoryDisplayInfo displayInfo) {
        super(ID, displayInfo, new HumiditySensorService());
    }

    public HumiditySensor(int ID, String label) {
        super(ID, label, new HumiditySensorService());
    }

    public HumiditySensor(int ID, AccessoryDisplayInfo displayInfo, String serviceName) {
        super(ID, displayInfo, new HumiditySensorService(serviceName));
    }

    public HumiditySensor(int ID, String label, String serviceName) {
        super(ID, label, new HumiditySensorService(serviceName));
    }

    public HumiditySensor setCurrentRelativeHumidityCallback(CharacteristicCallBack<Double> onCallbask) {
        getSpecificService(HumiditySensorService.class).
                getSpecificCharact(CurrentRelativeHumidityCharacteristic.class).setCallBack(onCallbask);
        return this;
    }

}
