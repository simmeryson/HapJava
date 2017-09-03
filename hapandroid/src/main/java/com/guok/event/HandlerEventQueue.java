package com.guok.event;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author guok
 */

public class HandlerEventQueue extends Thread implements EventQueue {
    public static final String TAG = HandlerEventQueue.class.getSimpleName();
    public static final int WHAT = 0x11a3;
    public static final String KEY = "HandlerEventQueue";

    private Handler mHandler;
    private final ExecutorService mService = Executors.newCachedThreadPool();

    public HandlerEventQueue() {
        mService.submit(this);
    }


    @Override
    public void enqueue(EventPair eventPair, final boolean consumed) {
        if (eventPair == null) return;
        Log.d("GK", TAG + " enqueue: " + eventPair.mEventType.getTarget());
        final EventPair pair = eventPair;
        final CountDownLatch downLatch = new CountDownLatch(1);
        ThreadMode threadMode = eventPair.mActionListener.schedulerMode();
        switch (threadMode) {
            case MAIN:
                new Handler(Looper.getMainLooper()).post(
                        new Runnable() {
                            @Override
                            public void run() {
                                sendToSubsciber(consumed, pair, downLatch);
                            }
                        }
                );
                break;
            case ASYNC:
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        sendToSubsciber(consumed, pair, downLatch);
                    }
                });
                break;
            case SYNC:
                sendToSubsciber(consumed, pair, downLatch);
                break;
            case LOOPER:
                Looper looper = pair.mActionListener.subcribeLooper();
                new Handler(looper).post(new Runnable() {
                    @Override
                    public void run() {
                        sendToSubsciber(consumed, pair, downLatch);
                    }
                });
                break;
        }
        if (pair.mEventType.getCallback() == null) return;

        try {
            downLatch.await(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pair.mEventType.getCallback().actionCallback(pair.mEventType);

    }

    private void sendToSubsciber(boolean consumed, EventPair pair, CountDownLatch downLatch) {
        if (consumed) {
            if (pair.mEventType.isDoned()) return;
            pair.mEventType.setDone(pair.mActionListener.onSubcribe(pair.mEventType));
        } else {
            pair.mActionListener.onSubcribe(pair.mEventType);
        }
        if (downLatch != null) downLatch.countDown();
    }

    @Override
    public void disable() {
        Looper.myLooper().quit();
    }

    @Override
    public void run() {
        Looper.prepare();
        mHandler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };
        Looper.loop();
    }
}
