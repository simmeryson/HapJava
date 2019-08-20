package com.guok.event;

import android.support.annotation.NonNull;

/**
 * 事件队列接口。
 *
 * @author guok
 */

public interface EventQueue {

    /**
     * 入队分发事件。
     *
     * @param eventPair 事件类型和监听器
     * @param consumed  是否消费事件
     */
    void enqueue(EventPair eventPair, boolean consumed);

    /**
     * 失效。队列不再分发事件。
     */
    void disable();

    /**
     * 事件类型和监听器
     */
    class EventPair {
        public final EventActionListener mActionListener;
        public final EventType mEventType;

        public EventPair(@NonNull EventActionListener actionListener, @NonNull EventType eventType) {
            mActionListener = actionListener;
            mEventType = eventType;
        }
    }
}
