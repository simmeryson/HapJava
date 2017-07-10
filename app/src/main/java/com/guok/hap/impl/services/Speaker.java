package com.guok.hap.impl.services;

import com.guok.hap.impl.characteristics.common.Name;
import com.guok.hap.impl.characteristics.media.On;

/**
 * @author guok
 */

public class Speaker extends BaseService {

    public Speaker() {
        this(null);
    }

    public Speaker(String serviceName) {
//        super("00000113-0000-1000-8000-0026BB765291");
        super("00000043-0000-1000-8000-0026BB765291");
        if (serviceName != null)
            addCharacteristic(new Name(serviceName));
        addCharacteristic(new On());

//        addCharacteristic(new MuteCharacteristic());
//        addCharacteristic(new VolumeCharacteristic());
    }
}
