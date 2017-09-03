package com.guok.event;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author guok
 */

public class RxjavaEventQueue implements EventQueue {

    private final CompositeDisposable mDisposable = new CompositeDisposable();
    private ObservableEmitter<EventPair> mEmitter;
    private Observable<EventPair> observable;

    public RxjavaEventQueue() {
        observable = Observable.create(new ObservableOnSubscribe<EventPair>() {
            @Override
            public void subscribe(ObservableEmitter<EventPair> emitter) throws Exception {
                mEmitter = emitter;
            }
        }).throttleFirst(1, TimeUnit.SECONDS);
    }

    @Override
    public void enqueue(EventPair eventPair, boolean consumed) {
        if (eventPair != null) {
            final CountDownLatch downLatch = new CountDownLatch(1);
            ThreadMode threadMode = eventPair.mActionListener.schedulerMode();
            switch (threadMode) {
                case MAIN:
                    setSubscriber(observable.observeOn(AndroidSchedulers.mainThread()), consumed, downLatch);
                    break;
                case ASYNC:
                    setSubscriber(observable.observeOn(Schedulers.newThread()), consumed, downLatch);
                    break;
                case SYNC:
                    setSubscriber(observable, consumed, downLatch);
                    break;
                case LOOPER:
                    setSubscriber(observable.observeOn(
                            AndroidSchedulers.from(eventPair.mActionListener.subcribeLooper())), consumed, downLatch);
                    break;
            }
            mEmitter.onNext(eventPair);

            if (eventPair.mEventType.getCallback() == null) return;

            try {
                downLatch.await(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            eventPair.mEventType.getCallback().actionCallback(eventPair.mEventType);

        }
    }

    private void setSubscriber(Observable<EventPair> observable, final boolean consumed, final CountDownLatch downLatch) {
        observable.subscribe(new Observer<EventPair>() {
            @Override
            public void onSubscribe(Disposable d) {
                mDisposable.add(d);
            }

            @Override
            public void onNext(EventPair pair) {
                if (consumed) {
                    if (pair.mEventType.isDoned()) return;
                    pair.mEventType.setDone(pair.mActionListener.onSubcribe(pair.mEventType));
                } else {
                    pair.mActionListener.onSubcribe(pair.mEventType);
                }
                if (downLatch != null) downLatch.countDown();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void disable() {
        mDisposable.clear();
    }
}
