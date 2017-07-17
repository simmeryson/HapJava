package com.guok.hap.impl.services;

import com.guok.hap.impl.characteristics.windowcovering.CurrentPositionCharacteristic;
import com.guok.hap.impl.characteristics.windowcovering.PositionStateCharacteristic;
import com.guok.hap.impl.characteristics.windowcovering.TargetPositionCharacteristic;

public class WindowCoveringService extends BaseService {

    public WindowCoveringService() {
        this(null);
    }

    public WindowCoveringService(String serviceName) {
        super("0000008C-0000-1000-8000-0026BB765291", serviceName);

        addCharacteristic(new TargetPositionCharacteristic());
        addCharacteristic(new CurrentPositionCharacteristic());
        addCharacteristic(new PositionStateCharacteristic());
    }
}
