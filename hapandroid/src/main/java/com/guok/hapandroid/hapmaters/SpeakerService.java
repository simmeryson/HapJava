package com.guok.hapandroid.hapmaters;

import com.guok.hap.impl.characteristics.common.Name;
import com.guok.hap.impl.characteristics.media.MuteCharacteristic;
import com.guok.hap.impl.characteristics.media.VolumeCharacteristic;
import com.guok.hap.impl.services.BaseService;

/**
 * @author guok
 */

public class SpeakerService extends BaseService {

    public SpeakerService() {
        this(null);
    }

    public SpeakerService(String serviceName) {
        super("00000113-0000-1000-8000-0026BB765291");
//        super("00000043-0000-1000-8000-0026BB765291");
        if (serviceName != null)
            addCharacteristic(new Name(serviceName));
//        addCharacteristic(new OnCharact());
//        addCharacteristic(new BrightnessCharact());

        addCharacteristic(new MuteCharacteristic());
        addCharacteristic(new VolumeCharacteristic());
    }
}
