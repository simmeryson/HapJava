package com.guok.hap.impl.characteristics.information;

import com.guok.hap.HomekitAccessory;
import com.guok.hap.characteristics.WriteOnlyBooleanCharacteristic;
import com.guok.hap.impl.responses.HapStatusCodes;

/**
 * This characteristic is used to cause the accessory to run its identify routine.
 */
public class Identify extends WriteOnlyBooleanCharacteristic {

    public static final String UUID = "00000014-0000-1000-8000-0026BB765291";

    private HomekitAccessory accessory;

    public Identify(HomekitAccessory accessory) throws Exception {
        super(UUID,
                "Identifies the accessory via a physical action on the accessory");
        this.accessory = accessory;
    }

    @Override
    public int setValue(Boolean value) throws Exception {
        if (value) {
            accessory.identify();
        }
        return HapStatusCodes.SUCCESS;
    }
}
