package com.guok.hap.accessories;

import com.guok.hap.AccessoryDisplayInfo;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.impl.accessories.BaseAccessory;
import com.guok.hap.impl.characteristics.thermostat.CurrentTemperatureCharacteristic;
import com.guok.hap.impl.services.TemperatureSensorService;

/**
 * A temperature sensor that reports the current temperature
 *
 * @author guokai
 */
public class TemperatureSensor extends BaseAccessory {

    public TemperatureSensor(int ID, AccessoryDisplayInfo displayInfo) {
        super(ID, displayInfo, new TemperatureSensorService());
    }

    public TemperatureSensor(int ID, String label) {
        super(ID, label, new TemperatureSensorService());
    }

    public TemperatureSensor(int ID, AccessoryDisplayInfo displayInfo, String serviceName) {
        super(ID, displayInfo, new TemperatureSensorService(serviceName));
    }

    public TemperatureSensor(int ID, String label, String serviceName) {
        super(ID, label, new TemperatureSensorService(serviceName));
    }

    public TemperatureSensor setCurrentTemperatureCallback(CharacteristicCallBack<Double> callbask) {
        getSpecificService(TemperatureSensorService.class).
                getSpecificCharact(CurrentTemperatureCharacteristic.class).setCallBack(callbask);
        return this;
    }
}
