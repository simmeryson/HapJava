package com.guok.hapandroid.server;

import com.guok.hap.impl.services.BaseService;

/**
 * @author guok
 */

public class KeywordService extends BaseService {

    public static final String UUID = "00000099-0000-1000-8000-0026BB765291";

    public KeywordService() {
        this(null);
    }

    public KeywordService(String serviceName) {
        super(UUID, serviceName);

        addCharacteristic(new KeywordCharacteristic());
    }
}
