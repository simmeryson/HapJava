package com.haierubic.os.homekitdemo.hapmaters;

import com.guok.hap.impl.characteristics.common.Name;
import com.guok.hap.impl.characteristics.media.MuteCharacteristic;
import com.guok.hap.impl.services.BaseService;

/**
 * @author guok
 */

public class Microphone extends BaseService {
    public Microphone() {
        super("00000112-0000-1000-8000-0026BB765291");
    }

    public Microphone(String serviceName){
        this();
        if (serviceName != null)
            addCharacteristic(new Name(serviceName));
        addCharacteristic(new MuteCharacteristic());
    }
}
