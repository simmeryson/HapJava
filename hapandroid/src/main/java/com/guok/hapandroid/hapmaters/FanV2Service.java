package com.guok.hapandroid.hapmaters;

import com.guok.hap.impl.characteristics.common.Name;
import com.guok.hap.impl.services.BaseService;

/**
 * @author guok
 */

public class FanV2Service extends BaseService {

    public FanV2Service() {
        super("000000B7-0000-1000-8000-0026BB765291");
    }

    public FanV2Service(String serviceName){
        this();
        if (serviceName != null)
            addCharacteristic(new Name(serviceName));

        addCharacteristic(new ActiveCharact());
    }
}
