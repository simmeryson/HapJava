package com.guok.event;

import com.guok.hapandroid.client.HomeKitControl;

/**
 * @author guok
 */

public class SubscribeMethodInfo {
    private String object;
    private Boolean isSubscribe;
    private HomeKitControl.SubscribeCallback subscribeCallback;

    public SubscribeMethodInfo(String object, Boolean isSubscribe, HomeKitControl.SubscribeCallback subscribeCallback) {
        this.object = object;
        this.isSubscribe = isSubscribe;
        this.subscribeCallback = subscribeCallback;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Boolean getSubscribe() {
        return isSubscribe;
    }

    public void setSubscribe(Boolean subscribe) {
        isSubscribe = subscribe;
    }

    public HomeKitControl.SubscribeCallback getSubscribeCallback() {
        return subscribeCallback;
    }

    public void setSubscribeCallback(HomeKitControl.SubscribeCallback subscribeCallback) {
        this.subscribeCallback = subscribeCallback;
    }
}
