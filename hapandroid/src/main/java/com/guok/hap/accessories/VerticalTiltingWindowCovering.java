package com.guok.hap.accessories;

import com.guok.hap.AccessoryDisplayInfo;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.impl.characteristics.windowcovering.CurrentVerticalTiltAngleCharacteristic;
import com.guok.hap.impl.characteristics.windowcovering.TargetVerticalTiltAngleCharacteristic;
import com.guok.hap.impl.services.WindowCoveringService;

/**
 * Extends WindowCovering with the ability to control vertical tilt angles
 *
 * @author guokai
 */
public class VerticalTiltingWindowCovering extends WindowCovering {

    public VerticalTiltingWindowCovering(int ID, AccessoryDisplayInfo displayInfo) {
        super(ID, displayInfo);
        addHorizontalCharacteristic();
    }

    public VerticalTiltingWindowCovering(int ID, String label) {
        super(ID, label);
        addHorizontalCharacteristic();
    }

    public VerticalTiltingWindowCovering(int ID, AccessoryDisplayInfo displayInfo, String serviceName) {
        super(ID, displayInfo, serviceName);
        addHorizontalCharacteristic();
    }

    public VerticalTiltingWindowCovering(int ID, String label, String serviceName) {
        super(ID, label, serviceName);
        addHorizontalCharacteristic();
    }

    private void addHorizontalCharacteristic() {
        getSpecificService(WindowCoveringService.class).addOptionalCharacteristic(new CurrentVerticalTiltAngleCharacteristic());
        getSpecificService(WindowCoveringService.class).addOptionalCharacteristic(new TargetVerticalTiltAngleCharacteristic());
    }

    public VerticalTiltingWindowCovering setCurrentTiltAngleCallback(CharacteristicCallBack<Integer> callback) {
        getSpecificService(WindowCoveringService.class).getSpecificCharact(CurrentVerticalTiltAngleCharacteristic.class).setCallBack(callback);
        return this;
    }

    public VerticalTiltingWindowCovering setTargetTiltAngleCallback(CharacteristicCallBack<Integer> callback) {
        getSpecificService(WindowCoveringService.class).getSpecificCharact(TargetVerticalTiltAngleCharacteristic.class).setCallBack(callback);
        return this;
    }
}
