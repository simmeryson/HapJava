package com.guok.hapandroid.server;

import com.guok.hap.characteristics.CharacteristicCallBack;

/**
 * @author guok
 */

public class KeywordCharacteristic extends StringCharacteristic {

    public static final String UUID = "0000009B-0000-1000-8000-0026BB765291";

    public KeywordCharacteristic() {
        this(null);
    }

    public KeywordCharacteristic(CharacteristicCallBack<String> callBack) {
        super(UUID, true, true, "keyword string", null);

        this.mCallBack = callBack;
    }
}
