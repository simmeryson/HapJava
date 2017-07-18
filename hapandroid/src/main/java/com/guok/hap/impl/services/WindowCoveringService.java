package com.guok.hap.impl.services;

import com.guok.hap.impl.characteristics.windowcovering.CurrentPositionCharacteristic;
import com.guok.hap.impl.characteristics.windowcovering.PositionStateCharacteristic;
import com.guok.hap.impl.characteristics.windowcovering.TargetPositionCharacteristic;

public class WindowCoveringService extends BaseService {

    public static final String UUID = "0000008C-0000-1000-8000-0026BB765291";

    public WindowCoveringService() {
        this(null);
    }

    public WindowCoveringService(String serviceName) {
        super(UUID, serviceName);

        addCharacteristic(new TargetPositionCharacteristic());
        addCharacteristic(new CurrentPositionCharacteristic());
        addCharacteristic(new PositionStateCharacteristic());
    }
}
