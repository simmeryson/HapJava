package com.guok.hapandroid.server;

import com.guok.hap.AccessoryDisplayInfo;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.impl.accessories.BaseAccessory;

/**
 * @author guok
 */

public class SearchEngine extends BaseAccessory {

    public static final String DOMAIN = "searcher";
    public static final String TARGET_KEYWORD = "keyword";
    public static final String TARGET_POWER = "power";

    public SearchEngine(int ID, AccessoryDisplayInfo displayInfo) {
        super(ID, displayInfo, new KeywordService());
    }

    public SearchEngine(int ID, String label) {
        super(ID, label, new KeywordService());
    }

    public SearchEngine(int ID, AccessoryDisplayInfo displayInfo, String serviceName) {
        super(ID, displayInfo, new KeywordService(serviceName));
    }

    public SearchEngine(int ID, String label, String serviceName) {
        super(ID, label, new KeywordService(serviceName));
    }

    public SearchEngine setOnCallback(CharacteristicCallBack<String> onCallbask) {
        getSpecificService(KeywordService.class).
                getSpecificCharact(KeywordCharacteristic.class).setCallBack(onCallbask);
        return this;
    }
}
