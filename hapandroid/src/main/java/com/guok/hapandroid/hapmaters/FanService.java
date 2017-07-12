package com.haierubic.os.homekitdemo.hapmaters;

import com.guok.hap.impl.characteristics.common.Name;
import com.guok.hap.impl.services.BaseService;

/**
 * @author guok
 */

public class FanService extends BaseService {
    public FanService() {
        super("00000040-0000-1000-8000-0026BB765291");
    }

    public FanService(String serviceName){
        this();
        if (serviceName != null)
            addCharacteristic(new Name(serviceName));
        addCharacteristic(new OnCharact());
    }
}
