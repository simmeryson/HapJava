package com.guok.event;

import android.os.Handler;
import android.os.Looper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author guok
 */

public class EventbusEventQueue implements EventQueue {

    private final EventBus mEventBus;

    public EventbusEventQueue() {
        mEventBus = EventBus.builder().build();
        mEventBus.register(this);
    }

    @Override
    public void enqueue(EventPair eventPair, boolean consumed) {
        if (eventPair == null) return;
        final CountDownLatch downLatch = new CountDownLatch(1);
        ThreadMode threadMode = eventPair.mActionListener.schedulerMode();
        if (threadMode == ThreadMode.MAIN) {
            mEventBus.post(new EventPairOnMain(eventPair, consumed, downLatch));
        } else if (threadMode == ThreadMode.ASYNC) {
            mEventBus.post(new EventPairAsync(eventPair, consumed, downLatch));
        } else if (threadMode == ThreadMode.SYNC) {
            mEventBus.post(new EventPairSync(eventPair, consumed, downLatch));
        } else if (threadMode == ThreadMode.LOOPER) {
            Looper looper = eventPair.mActionListener.subcribeLooper();
            final EventPair e = eventPair;
            final boolean c = consumed;
            new Handler(looper).post(new Runnable() {
                @Override
                public void run() {
                    if (c) {
                        if (e.mEventType.isDoned()) return;
                        e.mEventType.setDone(e.mActionListener.onSubcribe(e.mEventType));
                    } else {
                        e.mActionListener.onSubcribe(e.mEventType);
                    }
                    downLatch.countDown();
                }
            });
        }

        if (eventPair.mEventType.getCallback() == null) return;

        try {
            downLatch.await(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        eventPair.mEventType.getCallback().actionCallback(eventPair.mEventType);

    }

    @Override
    public void disable() {
        mEventBus.unregister(this);
    }


    @Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
    public void onEventMain(EventPairOnMain eventPair) {
        if (eventPair.consumed) {
            if (eventPair.mPair.mEventType.isDoned()) return;
            eventPair.mPair.mEventType.setDone(eventPair.mPair.mActionListener.onSubcribe(eventPair.mPair.mEventType));
        } else {
            eventPair.mPair.mActionListener.onSubcribe(eventPair.mPair.mEventType);
        }
        if (eventPair.downLatch != null) eventPair.downLatch.countDown();
    }

    @Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.ASYNC)
    public void onEventAsyc(EventPairAsync eventPair) {
        if (eventPair.consumed) {
            if (eventPair.mPair.mEventType.isDoned()) return;
            eventPair.mPair.mEventType.setDone(eventPair.mPair.mActionListener.onSubcribe(eventPair.mPair.mEventType));
        } else {
            eventPair.mPair.mActionListener.onSubcribe(eventPair.mPair.mEventType);
        }
        if (eventPair.downLatch != null) eventPair.downLatch.countDown();
    }

    @Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.POSTING)
    public void onEventLooper(EventPairSync eventPair) {
        if (eventPair.consumed) {
            if (eventPair.mPair.mEventType.isDoned()) return;
            eventPair.mPair.mEventType.setDone(eventPair.mPair.mActionListener.onSubcribe(eventPair.mPair.mEventType));
        } else {
            eventPair.mPair.mActionListener.onSubcribe(eventPair.mPair.mEventType);
        }
        if (eventPair.downLatch != null) eventPair.downLatch.countDown();
    }

    public class EventPairOnMain {
        final EventPair mPair;
        final boolean consumed;
        final CountDownLatch downLatch;

        public EventPairOnMain(EventPair pair, boolean consumed, CountDownLatch downLatch) {
            mPair = pair;
            this.consumed = consumed;
            this.downLatch = downLatch;
        }
    }

    public class EventPairAsync {
        final EventPair mPair;
        final boolean consumed;
        final CountDownLatch downLatch;

        public EventPairAsync(EventPair pair, boolean consumed, CountDownLatch downLatch) {
            mPair = pair;
            this.consumed = consumed;
            this.downLatch = downLatch;
        }
    }

    public class EventPairSync {
        final EventPair mPair;
        final boolean consumed;
        final CountDownLatch downLatch;

        public EventPairSync(EventPair pair, boolean consumed, CountDownLatch downLatch) {
            mPair = pair;
            this.consumed = consumed;
            this.downLatch = downLatch;
        }
    }
}
