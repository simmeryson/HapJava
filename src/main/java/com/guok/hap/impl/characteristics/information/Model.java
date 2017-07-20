package com.guok.hap.impl.characteristics.information;

import com.guok.hap.HomekitAccessory;
import com.guok.hap.characteristics.StaticStringCharacteristic;

public class Model extends StaticStringCharacteristic {

    public static final String UUID = "00000021-0000-1000-8000-0026BB765291";

    public Model(HomekitAccessory accessory) throws Exception {
        super(UUID, "The name of the model", accessory.getModel());
    }

}
