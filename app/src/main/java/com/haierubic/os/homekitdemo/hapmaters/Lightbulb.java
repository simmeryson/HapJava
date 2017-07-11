package com.haierubic.os.homekitdemo.hapmaters;

import com.guok.hap.impl.characteristics.common.Name;
import com.guok.hap.impl.services.BaseService;

/**
 * @author guok
 */

public class Lightbulb extends BaseService{

    public Lightbulb(){
        this(null);
    }
    public Lightbulb(String serviceName) {
        super("00000043-0000-1000-8000-0026BB765291");

        if (serviceName != null)
            addCharacteristic(new Name(serviceName));

        addCharacteristic(new OnCharact());
    }
}
