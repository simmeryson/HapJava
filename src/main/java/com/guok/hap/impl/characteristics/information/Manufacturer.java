package com.guok.hap.impl.characteristics.information;

import com.guok.hap.HomekitAccessory;
import com.guok.hap.characteristics.StaticStringCharacteristic;

public class Manufacturer extends StaticStringCharacteristic {

    public static final String UUID = "00000020-0000-1000-8000-0026BB765291";

    public Manufacturer(HomekitAccessory accessory) throws Exception {
        super(UUID, "The name of the manufacturer", accessory.getManufacturer());
    }

}
