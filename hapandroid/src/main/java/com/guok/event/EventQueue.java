package com.guok.event;

import android.support.annotation.NonNull;

/**
 * @author guok
 */

public interface EventQueue {

    void enqueue(EventPair eventPair, boolean consumed);

    void disable();

    class EventPair {
        public final EventActionListener mActionListener;
        public final EventType mEventType;

        public EventPair(@NonNull EventActionListener actionListener, @NonNull EventType eventType) {
            mActionListener = actionListener;
            mEventType = eventType;
        }
    }
}
