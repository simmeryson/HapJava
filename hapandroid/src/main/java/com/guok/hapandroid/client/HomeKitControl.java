package com.guok.hapandroid.client;

import com.guok.hapandroid.HapValueVO;

/**
 * @author guok
 */

public interface HomeKitControl {

    void subscribe(String object, Boolean isSubscribe, SubscribeCallback subscribeCallback);
    void setValue(String object, String newValue);
    HapValueVO getValue(String object);

    interface SubscribeCallback{
        void change(HapValueVO value);
    }
}
