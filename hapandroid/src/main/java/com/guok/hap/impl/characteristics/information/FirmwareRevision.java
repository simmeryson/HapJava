package com.guok.hap.impl.characteristics.information;

import com.guok.hap.HomekitAccessory;
import com.guok.hap.characteristics.StaticStringCharacteristic;

/**
 * @author guokai
 */
public class FirmwareRevision extends StaticStringCharacteristic {

    public FirmwareRevision(HomekitAccessory accessory) {
        super("00000052-0000-1000-8000-0026BB765291", "Firmware Revision", accessory.getFirmwareRevision()
        );
    }
}
