package com.guok.hap.impl.services;

import com.guok.hap.impl.characteristics.media.MuteCharacteristic;

/**
 * This service requires iOS 10.3
 *
 * @author guok
 */

public class MicrophoneService extends BaseService {
    public MicrophoneService() {
        this(null);
    }

    public MicrophoneService(String serviceName) {
        super("00000112-0000-1000-8000-0026BB765291", serviceName);

        addCharacteristic(new MuteCharacteristic());
    }
}
