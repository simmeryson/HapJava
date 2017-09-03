package com.guok.event;

import android.os.Looper;

/**
 * mode 0 表示主线程订阅
 * 1 表示异步线程
 * 2 表示指定线程
 *
 * @author guok
 */

public interface EventActionListener {

    boolean onSubcribe(EventType eventType);

    ThreadMode schedulerMode();

    Looper subcribeLooper();

    abstract class OnMain implements EventActionListener {
        @Override
        public ThreadMode schedulerMode() {
            return ThreadMode.MAIN;
        }

        @Override
        public Looper subcribeLooper() {
            return null;
        }
    }

    abstract class Async implements EventActionListener {
        @Override
        public ThreadMode schedulerMode() {
            return ThreadMode.ASYNC;
        }

        @Override
        public Looper subcribeLooper() {
            return null;
        }
    }


    abstract class InLooper implements EventActionListener {
        public final Looper mLooper;

        public InLooper(Looper looper) {
            mLooper = looper;
        }

        @Override
        public ThreadMode schedulerMode() {
            return ThreadMode.LOOPER;
        }

        @Override
        public Looper subcribeLooper() {
            return mLooper;
        }
    }
}
