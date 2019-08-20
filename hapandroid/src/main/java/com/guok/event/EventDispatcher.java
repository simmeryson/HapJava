package com.guok.event;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 事件分发类，根据事件类型把监听器区分开，同一类事件只会发送给订阅该事件的监听器。
 *
 * @author guok
 */

public class EventDispatcher {

    private final static EventDispatcher instance = new EventDispatcher();
    private final Collection<EventListener> mListeners = Collections.synchronizedCollection(new ArrayList<EventListener>());
    private volatile EventQueue mEventQueue;

    private final Map<String, Set<EventActionListener>> subscribers = new ConcurrentHashMap<>();

    private EventDispatcher() {
    }

    public static EventDispatcher getInstance() {
        return instance;
    }

    public boolean addListener(EventListener listener) {
        return listener != null && mListeners.add(listener);
    }

    public boolean removeListener(EventListener listener) {
        return listener != null && mListeners.remove(listener);
    }

    public void clearListener() {
        mListeners.clear();
        subscribers.clear();
    }

    /**
     * 事件分发。根据事件的类型找到监听器，通过队列接口来分发事件。
     *
     * @param event    事件
     * @param consumed 事件是否消费掉。如果事件被消费就不会发给后面的监听器。可能会引起队列接口的线程阻塞。
     * @param actionCallback 执行回调。
     */
    public void dispatchEvent(Event event, boolean consumed, ActionCallback actionCallback) {
        if (mEventQueue != null && event != null) {
            EventType eventType = EventType.fromEvent(event);
            if (eventType == null) return;
            dispatchEvent(eventType, consumed, actionCallback);
        }
    }

    /**
     * 事件分发。根据事件的类型找到监听器，通过队列接口来分发事件。
     *
     * @param eventType 事件类型
     * @param consumed 事件是否消费掉。如果事件被消费就不会发给后面的监听器。可能会引起队列接口的线程阻塞。
     * @param actionCallback 执行回调。
     */
    public void dispatchEvent(EventType eventType, boolean consumed, ActionCallback actionCallback) {
        if (mEventQueue != null && eventType != null) {
            Set<EventActionListener> listeners = subscribers.get(eventType.getTarget());
            if (listeners == null) return;
            eventType.setCallback(actionCallback);
            for (EventActionListener actionListener : listeners) {
                if (consumed && eventType.isDoned()) return;
                mEventQueue.enqueue(new EventQueue.EventPair(actionListener, eventType), consumed);
            }
        }
    }

    @Deprecated
    boolean dispatch(Event event) {
        for (EventListener listener : mListeners) {
            if (listener.onEvent(event)) {
                return true;
            }
        }
        return false;
    }


    @Deprecated
    public void subscribe(Event event, boolean once) {
        EventType eventType = EventType.fromEvent(event);
        if (eventType != null) {
            Set<EventActionListener> callbacks = subscribers.get(eventType.getTarget());
            if (callbacks == null) return;
            for (EventActionListener actionListener : callbacks) {
                if (actionListener.onSubcribe(eventType) && once)
                    return;
            }
        }
    }

    @Deprecated
    public void subscribe(Event event) {
        subscribe(event, true);
    }


    public void registerEventType(String eventType, EventActionListener listener) {
        if (eventType == null || listener == null)
            return;
        Set<EventActionListener> actionListeners = subscribers.get(eventType);
        if (actionListeners == null) {
            actionListeners = Collections.synchronizedSet(new LinkedHashSet<EventActionListener>());
        }
        actionListeners.add(listener);
        subscribers.put(eventType, actionListeners);
    }

    public void registerEventType(Map<String, EventActionListener> map) {
        registerEventType(Collections.singletonList(map));
    }

    public void registerEventType(List<Map<String, EventActionListener>> list) {
        if (list != null && list.size() > 0) {
            for (Map<String, EventActionListener> map : list) {
                for (Map.Entry<String, EventActionListener> entry : map.entrySet()) {
                    String key = entry.getKey();
                    Set<EventActionListener> actionListeners = subscribers.get(key);
                    if (actionListeners == null) {
                        actionListeners = Collections.synchronizedSet(new LinkedHashSet<EventActionListener>());
                        subscribers.put(key, actionListeners);
                    }
                    actionListeners.add(entry.getValue());
                }
            }
        }
    }

    public void unregisterEventType(Map<String, EventActionListener> map) {
        if (map != null && map.size() > 0) {
            for (Map.Entry<String, EventActionListener> entry : map.entrySet()) {
                String key = entry.getKey();
                EventActionListener value = entry.getValue();
                Set<EventActionListener> listeners = subscribers.get(key);
                if (listeners == null) {
                    listeners = Collections.synchronizedSet(new LinkedHashSet<EventActionListener>());
                    subscribers.put(key, listeners);
                }
                listeners.remove(value);
            }
        }
    }

    public void unregisterEventType(String eventType, EventActionListener actionListener) {
        if (eventType != null) {
            Set<EventActionListener> listeners = subscribers.get(eventType);
            if (listeners == null) {
                listeners = Collections.synchronizedSet(new LinkedHashSet<EventActionListener>());
                subscribers.put(eventType, listeners);
            }
            listeners.remove(actionListener);
        }
    }

    public void unregisterEventType(List<Map<String, EventActionListener>> list) {
        if (list != null && list.size() > 0) {
            for (Map<String, EventActionListener> map : list) {
                for (Map.Entry<String, EventActionListener> entry : map.entrySet()) {
                    String key = entry.getKey();
                    Set<EventActionListener> actionListeners = subscribers.get(key);
                    if (actionListeners == null) {
                        actionListeners = Collections.synchronizedSet(new LinkedHashSet<EventActionListener>());
                        subscribers.put(key, actionListeners);
                    }
                    actionListeners.remove(entry.getValue());
                }
            }
        }
    }

    public void disable() {
        if (mEventQueue != null)
            mEventQueue.disable();
    }

    public Map<String, Set<EventActionListener>> getSubscribers() {
        return subscribers;
    }

    public Collection<EventListener> getListeners() {
        return mListeners;
    }

    public EventQueue getEventQueue() {
        return mEventQueue;
    }

    public void setEventQueue(EventQueue eventQueue) {
        mEventQueue = eventQueue;
    }


    public static Class getGenericType(Object o, int index) {
        Type genType = o.getClass().getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            throw new RuntimeException("Index outof bounds");
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }
}
