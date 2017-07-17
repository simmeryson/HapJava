package com.guok.hap.impl.services;

import com.guok.hap.impl.characteristics.media.MuteCharacteristic;
import com.guok.hap.impl.services.BaseService;

/**
 * This service requires iOS 10.3
 *
 * @author guok
 */

public class SpeakerService extends BaseService {

    public SpeakerService() {
        this(null);
    }

    public SpeakerService(String serviceName) {
        super("00000113-0000-1000-8000-0026BB765291", serviceName);

        addCharacteristic(new MuteCharacteristic());
    }
}
