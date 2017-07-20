package com.guok.hap.accessories;

import com.guok.hap.AccessoryDisplayInfo;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.impl.characteristics.lightbulb.HueCharacteristic;
import com.guok.hap.impl.services.LightbulbService;


/**
 * Extends {@link Lightbulb} with color settings. This will usually be implemented along with
 * {@link DimmableLightbulb}, but not necessarily so.
 *
 * @author guokai
 */
public class ColorfulLightbulb extends Lightbulb {

    public ColorfulLightbulb(int ID, String label) {
        super(ID, label);

        getSpecificService(LightbulbService.class).addOptionalCharacteristic(new HueCharacteristic());
    }

    public ColorfulLightbulb(int ID, String label, String serviceName) {
        super(ID, label, serviceName);

        getSpecificService(LightbulbService.class).addOptionalCharacteristic(new HueCharacteristic());
    }

    public ColorfulLightbulb(int ID, AccessoryDisplayInfo displayInfo, String serviceName) {
        super(ID, displayInfo, serviceName);

        getSpecificService(LightbulbService.class).addOptionalCharacteristic(new HueCharacteristic());
    }

    public ColorfulLightbulb(int ID, AccessoryDisplayInfo displayInfo) {
        super(ID, displayInfo);

        getSpecificService(LightbulbService.class).addOptionalCharacteristic(new HueCharacteristic());
    }

    public ColorfulLightbulb setHueCallback(CharacteristicCallBack<Double> hueCallback) {
        getSpecificService(LightbulbService.class).getSpecificCharact(HueCharacteristic.class).setCallBack(hueCallback);
        return this;
    }
}
