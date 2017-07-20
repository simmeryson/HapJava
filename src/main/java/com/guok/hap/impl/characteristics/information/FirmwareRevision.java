package com.guok.hap.impl.characteristics.information;

import com.guok.hap.HomekitAccessory;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.StaticStringCharacteristic;

/**
 * @author guokai
 */
public class FirmwareRevision extends StaticStringCharacteristic {

    public static final String UUID = "00000052-0000-1000-8000-0026BB765291";

    public FirmwareRevision() {
        this((HomekitAccessory) null);
    }

    public FirmwareRevision(CharacteristicCallBack<String> callBack) {
        this((HomekitAccessory) null);
        this.mCallBack = callBack;
    }

    public FirmwareRevision(HomekitAccessory accessory) {
        super(UUID, "Firmware Revision", accessory.getFirmwareRevision()
        );
    }
}
