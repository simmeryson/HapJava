package com.guok.hap.impl.services;

import com.guok.hap.impl.characteristics.media.MuteCharacteristic;

/**
 * This service requires iOS 10.3
 *
 * @author guok
 */

public class MicrophoneService extends BaseService {

    public static final String UUID = "00000112-0000-1000-8000-0026BB765291";

    public MicrophoneService() {
        this(null);
    }

    public MicrophoneService(String serviceName) {
        super(UUID, serviceName);

        addCharacteristic(new MuteCharacteristic());
    }
}
