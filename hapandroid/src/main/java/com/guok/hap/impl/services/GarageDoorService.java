package com.guok.hap.impl.services;

import com.guok.hap.impl.characteristics.common.ObstructionDetectedCharacteristic;
import com.guok.hap.impl.characteristics.garage.CurrentDoorStateCharacteristic;
import com.guok.hap.impl.characteristics.garage.TargetDoorStateCharacteristic;

public class GarageDoorService extends BaseService {

    public GarageDoorService() {
        this(null);
    }

    public GarageDoorService(String serviceName) {
        super("00000041-0000-1000-8000-0026BB765291", serviceName);

        addCharacteristic(new CurrentDoorStateCharacteristic());
        addCharacteristic(new TargetDoorStateCharacteristic());
        addCharacteristic(new ObstructionDetectedCharacteristic());
    }

}
