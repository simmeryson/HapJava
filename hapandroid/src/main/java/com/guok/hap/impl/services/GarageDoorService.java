package com.guok.hap.impl.services;

import com.guok.hap.impl.characteristics.common.ObstructionDetectedCharacteristic;
import com.guok.hap.impl.characteristics.garage.CurrentDoorStateCharacteristic;
import com.guok.hap.impl.characteristics.garage.TargetDoorStateCharacteristic;

public class GarageDoorService extends BaseService {

    public static final String UUID = "00000041-0000-1000-8000-0026BB765291";

    public GarageDoorService() {
        this(null);
    }

    public GarageDoorService(String serviceName) {
        super(UUID, serviceName);

        addCharacteristic(new CurrentDoorStateCharacteristic());
        addCharacteristic(new TargetDoorStateCharacteristic());
        addCharacteristic(new ObstructionDetectedCharacteristic());
    }

}
