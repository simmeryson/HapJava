package com.haierubic.os.homekitdemo.hapmaters;

import com.guok.hap.impl.characteristics.common.Name;
import com.guok.hap.impl.services.BaseService;

/**
 * @author guok
 */

public class CarbonMonoxideSensorService extends BaseService {
    public CarbonMonoxideSensorService() {
        super("0000007F-0000-1000-8000-0026BB765291");
    }

    public CarbonMonoxideSensorService(String serviceName){
        this();
        if (serviceName != null)
            addCharacteristic(new Name(serviceName));
        addCharacteristic(new CarbonMonoxideDetectedCharact());
    }
}
