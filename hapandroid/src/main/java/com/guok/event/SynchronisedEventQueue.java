package com.guok.event;

/**
 * @author guok
 */

public class SynchronisedEventQueue implements EventQueue {
    private boolean disable = false;

    public SynchronisedEventQueue() {
    }

    @Override
    public void enqueue(EventPair pair, boolean consumed) {
        if (!disable) {
            if (consumed) {
                if (pair.mEventType.isDoned()) return;
                pair.mEventType.setDone(pair.mActionListener.onSubcribe(pair.mEventType));
            } else {
                pair.mActionListener.onSubcribe(pair.mEventType);
            }
            ActionCallback callback = pair.mEventType.getCallback();
            if (callback != null) callback.actionCallback(pair.mEventType);
        }
    }

    @Override
    public void disable() {
        disable = true;
    }
}
