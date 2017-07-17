package com.guok.hap.impl.services;

import com.guok.hap.impl.characteristics.contactsensor.ContactSensorStateCharacteristic;

public class ContactSensorService extends BaseService {

    public ContactSensorService() {
        this(null);
    }

    public ContactSensorService(String serviceName) {
        super("00000080-0000-1000-8000-0026BB765291", serviceName);

        addCharacteristic(new ContactSensorStateCharacteristic());
    }
}
