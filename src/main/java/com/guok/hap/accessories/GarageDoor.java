package com.guok.hap.accessories;

import com.guok.hap.AccessoryDisplayInfo;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.impl.accessories.BaseAccessory;
import com.guok.hap.impl.characteristics.common.ObstructionDetectedCharacteristic;
import com.guok.hap.impl.characteristics.garage.CurrentDoorStateCharacteristic;
import com.guok.hap.impl.characteristics.garage.TargetDoorStateCharacteristic;
import com.guok.hap.impl.services.GarageDoorService;

/**
 * A garage door opener, with control and status of a garage door
 *
 * @author guokai
 */
public class GarageDoor extends BaseAccessory {

    public GarageDoor(int ID, AccessoryDisplayInfo displayInfo) {
        super(ID, displayInfo, new GarageDoorService());
    }

    public GarageDoor(int ID, String label) {
        super(ID, label, new GarageDoorService());
    }

    public GarageDoor(int ID, AccessoryDisplayInfo displayInfo, String serviceName) {
        super(ID, displayInfo, new GarageDoorService(serviceName));
    }

    public GarageDoor(int ID, String label, String serviceName) {
        super(ID, label, new GarageDoorService(serviceName));
    }

    public GarageDoor setCurrentDoorStateCallback(CharacteristicCallBack<Integer> callBack) {
        getSpecificService(GarageDoorService.class).
                getSpecificCharact(CurrentDoorStateCharacteristic.class).setCallBack(callBack);
        return this;
    }

    public GarageDoor setTargetDoorStateCallback(CharacteristicCallBack<Integer> callBack) {
        getSpecificService(GarageDoorService.class).
                getSpecificCharact(TargetDoorStateCharacteristic.class).setCallBack(callBack);
        return this;
    }

    public GarageDoor setObstructionDetectedCallback(CharacteristicCallBack<Boolean> callBack) {
        getSpecificService(GarageDoorService.class).
                getSpecificCharact(ObstructionDetectedCharacteristic.class).setCallBack(callBack);
        return this;
    }
}
