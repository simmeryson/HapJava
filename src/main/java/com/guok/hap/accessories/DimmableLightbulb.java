package com.guok.hap.accessories;

import com.guok.hap.AccessoryDisplayInfo;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.impl.characteristics.lightbulb.BrightnessCharacteristic;
import com.guok.hap.impl.services.LightbulbService;

/**
 * Extends {@link Lightbulb} with brightness values.
 *
 * @author guokai
 */
public class DimmableLightbulb extends Lightbulb {

    protected CharacteristicCallBack<Integer> mBrightCallback;

    public DimmableLightbulb(int ID, String label) {
        super(ID, label);

        getSpecificService(LightbulbService.class).addOptionalCharacteristic(new BrightnessCharacteristic());
    }

    public DimmableLightbulb(int ID, String label, String serviceName) {
        super(ID, label, serviceName);

        getSpecificService(LightbulbService.class).addOptionalCharacteristic(new BrightnessCharacteristic());
    }

    public DimmableLightbulb(int ID, AccessoryDisplayInfo displayInfo, String serviceName) {
        super(ID, displayInfo, serviceName);

        getSpecificService(LightbulbService.class).addOptionalCharacteristic(new BrightnessCharacteristic());
    }

    public DimmableLightbulb(int ID, AccessoryDisplayInfo displayInfo) {
        super(ID, displayInfo);

        getSpecificService(LightbulbService.class).addOptionalCharacteristic(new BrightnessCharacteristic());
    }

    public DimmableLightbulb setBrightCallback(CharacteristicCallBack<Integer> brightCallback) {
        getSpecificService(LightbulbService.class).getSpecificCharact(BrightnessCharacteristic.class).setCallBack(brightCallback);
        return this;
    }
}
