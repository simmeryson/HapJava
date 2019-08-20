package com.guok.event;

import com.guok.hapandroid.client.HomeKitControl;

/**
 * HomeKit订阅方法的封装类。
 * object 代表需要订阅的对象(domain和target)。
 * isSubscribe 代表是否订阅。
 * subscribeCallback 代表订阅回调函数，一般是#HomeKitClientReceiver.sendToHomekit()
 *
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
