package com.guok.hap.accessories;

import com.guok.hap.AccessoryDisplayInfo;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.impl.accessories.BaseAccessory;
import com.guok.hap.impl.characteristics.carbonmonoxide.CarbonMonoxideDetectedCharacteristic;
import com.guok.hap.impl.services.CarbonMonoxideSensorService;

/**
 * <p>A carbon monoxide sensor reports whether carbon monoxide has been detected or not.</p>
 * <p>
 * <p>Carbon monoxide sensors that run on batteries will need to implement this interface
 * and also implement {@link BatteryAccessory}.</p>
 *
 * @author guokai
 */
public class CarbonMonoxideSensor extends BaseAccessory {

    public CarbonMonoxideSensor(int ID, AccessoryDisplayInfo displayInfo) {
        super(ID, displayInfo, new CarbonMonoxideSensorService());
    }

    public CarbonMonoxideSensor(int ID, String label) {
        super(ID, label, new CarbonMonoxideSensorService());
    }

    public CarbonMonoxideSensor(int ID, AccessoryDisplayInfo displayInfo, String serviceName) {
        super(ID, displayInfo, new CarbonMonoxideSensorService(serviceName));
    }

    public CarbonMonoxideSensor(int ID, String label, String serviceName) {
        super(ID, label, new CarbonMonoxideSensorService(serviceName));
    }

    public CarbonMonoxideSensor setCarbonMonoxideDetectedCallback(CharacteristicCallBack<Integer> callbask) {
        getSpecificService(CarbonMonoxideSensorService.class).
                getSpecificCharact(CarbonMonoxideDetectedCharacteristic.class).setCallBack(callbask);
        return this;
    }
}
