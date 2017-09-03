package com.guok.event;

import android.text.TextUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 客户端订阅事件类。在Activity或Fragment使用。
 *
 * @author guok
 */
public class EventSubscriber {

    private EventDispatcher mEventDispatcher = EventDispatcher.getInstance();
    private Map<String, EventActionListener> mNlpListenerMap = new ConcurrentHashMap<>();

    public EventDispatcher getEventDispatcher() {
        return mEventDispatcher;
    }

    public void registerNlpEvent(String eventType, EventActionListener listener) {
        mNlpListenerMap.put(eventType, listener);
        mEventDispatcher.registerEventType(mNlpListenerMap);
    }

    public void unregisterNlpEvent(String eventType) {
        if (!TextUtils.isEmpty(eventType)) {
            EventActionListener actionListener = mNlpListenerMap.remove(eventType);
            mEventDispatcher.unregisterEventType(eventType, actionListener);
        }
    }

    public void clearRegisterNlpEvent() {
        for (String key : mNlpListenerMap.keySet()) {
            if (!TextUtils.isEmpty(key)) {
                EventActionListener actionListener = mNlpListenerMap.get(key);
                mEventDispatcher.unregisterEventType(key, actionListener);
            }
        }
        mNlpListenerMap.clear();
    }
}
