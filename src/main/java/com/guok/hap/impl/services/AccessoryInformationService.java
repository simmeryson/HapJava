package com.guok.hap.impl.services;

import com.guok.hap.HomekitAccessory;
import com.guok.hap.impl.characteristics.information.FirmwareRevision;
import com.guok.hap.impl.characteristics.information.Identify;
import com.guok.hap.impl.characteristics.information.Manufacturer;
import com.guok.hap.impl.characteristics.information.Model;
import com.guok.hap.impl.characteristics.information.SerialNumber;

public class AccessoryInformationService extends BaseService {

    public static final String UUID = "0000003E-0000-1000-8000-0026BB765291";

    public AccessoryInformationService(HomekitAccessory accessory) throws Exception {
        this(accessory, accessory.getLabel());
    }

    public AccessoryInformationService(HomekitAccessory accessory, String serviceName) throws Exception {
        super(UUID, serviceName);

        addCharacteristic(new Manufacturer(accessory));
        addCharacteristic(new Model(accessory));
        addCharacteristic(new SerialNumber(accessory));
        addCharacteristic(new Identify(accessory));
        addCharacteristic(new FirmwareRevision(accessory));
    }

}
