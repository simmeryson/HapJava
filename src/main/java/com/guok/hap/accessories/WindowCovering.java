package com.guok.hap.accessories;

import com.guok.hap.AccessoryDisplayInfo;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.impl.accessories.BaseAccessory;
import com.guok.hap.impl.characteristics.windowcovering.CurrentPositionCharacteristic;
import com.guok.hap.impl.characteristics.windowcovering.PositionStateCharacteristic;
import com.guok.hap.impl.characteristics.windowcovering.TargetPositionCharacteristic;
import com.guok.hap.impl.services.WindowCoveringService;

/**
 * A window covering, like blinds, which can be remotely controlled.
 *
 * @author guokai
 */
public class WindowCovering extends BaseAccessory {

    public WindowCovering(int ID, AccessoryDisplayInfo displayInfo) {
        super(ID, displayInfo, new WindowCoveringService());
    }

    public WindowCovering(int ID, String label) {
        super(ID, label, new WindowCoveringService());
    }

    public WindowCovering(int ID, AccessoryDisplayInfo displayInfo, String serviceName) {
        super(ID, displayInfo, new WindowCoveringService(serviceName));
    }

    public WindowCovering(int ID, String label, String serviceName) {
        super(ID, label, new WindowCoveringService(serviceName));
    }

    public WindowCovering setTargetPositionCallback(CharacteristicCallBack<Integer> callBack) {
        getSpecificService(WindowCoveringService.class).
                getSpecificCharact(TargetPositionCharacteristic.class).setCallBack(callBack);
        return this;
    }

    public WindowCovering setCurrentPositionCallback(CharacteristicCallBack<Integer> callBack) {
        getSpecificService(WindowCoveringService.class).
                getSpecificCharact(CurrentPositionCharacteristic.class).setCallBack(callBack);
        return this;
    }

    public WindowCovering setPositionStateCallback(CharacteristicCallBack<Integer> callBack) {
        getSpecificService(WindowCoveringService.class).
                getSpecificCharact(PositionStateCharacteristic.class).setCallBack(callBack);
        return this;
    }
}
