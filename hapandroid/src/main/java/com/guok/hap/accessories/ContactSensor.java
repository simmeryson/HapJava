package com.guok.hap.accessories;

import com.guok.hap.AccessoryDisplayInfo;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.impl.accessories.BaseAccessory;
import com.guok.hap.impl.characteristics.contactsensor.ContactSensorStateCharacteristic;
import com.guok.hap.impl.services.ContactSensorService;

/**
 * <p>A contact sensor that reports whether contact is detected or not. Typical
 * contact sensors are window/door sensors. When contact is detected it means
 * that the door/window is closed.</p>
 * <p>
 * <p>Contact sensors that run on batteries will need to implement this interface
 * and also implement {@link BatteryAccessory}.</p>
 *
 * @author guokai
 */
public class ContactSensor extends BaseAccessory {

    public ContactSensor(int ID, AccessoryDisplayInfo displayInfo) {
        super(ID, displayInfo, new ContactSensorService());
    }

    public ContactSensor(int ID, String label) {
        super(ID, label, new ContactSensorService());
    }

    public ContactSensor(int ID, AccessoryDisplayInfo displayInfo, String serviceName) {
        super(ID, displayInfo, new ContactSensorService(serviceName));
    }

    public ContactSensor(int ID, String label, String serviceName) {
        super(ID, label, new ContactSensorService(serviceName));
    }

    public ContactSensor setContactSensorStateCallback(CharacteristicCallBack<Integer> callbask) {
        getSpecificService(ContactSensorService.class).
                getSpecificCharact(ContactSensorStateCharacteristic.class).setCallBack(callbask);
        return this;
    }
}
