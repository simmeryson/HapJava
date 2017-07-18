package com.guok.hap.accessories;

import com.guok.hap.AccessoryDisplayInfo;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.impl.characteristics.windowcovering.CurrentHorizontalTiltAngleCharacteristic;
import com.guok.hap.impl.characteristics.windowcovering.TargetHorizontalTiltAngleCharacteristic;
import com.guok.hap.impl.services.WindowCoveringService;

/**
 * Extends WindowCovering with the ability to control horizontal tilt angles
 *
 * @author Andy Lintner
 */
public class HorizontalTiltingWindowCovering extends WindowCovering {

    public HorizontalTiltingWindowCovering(int ID, AccessoryDisplayInfo displayInfo) {
        super(ID, displayInfo);
        addHorizontalCharacteristic();
    }


    public HorizontalTiltingWindowCovering(int ID, String label) {
        super(ID, label);
        addHorizontalCharacteristic();
    }

    public HorizontalTiltingWindowCovering(int ID, AccessoryDisplayInfo displayInfo, String serviceName) {
        super(ID, displayInfo, serviceName);
        addHorizontalCharacteristic();
    }

    public HorizontalTiltingWindowCovering(int ID, String label, String serviceName) {
        super(ID, label, serviceName);
        addHorizontalCharacteristic();
    }

    private void addHorizontalCharacteristic() {
        getSpecificService(WindowCoveringService.class).addOptionalCharacteristic(new CurrentHorizontalTiltAngleCharacteristic());
        getSpecificService(WindowCoveringService.class).addOptionalCharacteristic(new TargetHorizontalTiltAngleCharacteristic());
    }

    public HorizontalTiltingWindowCovering setCurrentTiltAngleCallback(CharacteristicCallBack<Integer> callback) {
        getSpecificService(WindowCoveringService.class).getSpecificCharact(CurrentHorizontalTiltAngleCharacteristic.class).setCallBack(callback);
        return this;
    }

    public HorizontalTiltingWindowCovering setTargetTiltAngleCallback(CharacteristicCallBack<Integer> callback) {
        getSpecificService(WindowCoveringService.class).getSpecificCharact(TargetHorizontalTiltAngleCharacteristic.class).setCallBack(callback);
        return this;
    }
}
