package com.guok.hap.impl.characteristics.common;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.characteristics.IntegerCharacteristic;
import com.guok.hap.impl.Consumer;
import com.guok.hap.impl.Supplier;


public class BatteryLevelCharacteristic extends IntegerCharacteristic implements EventableCharacteristic {

    private final Supplier<ListenableFuture<Integer>> getter;
    private final Consumer<HomekitCharacteristicChangeCallback> subscriber;
    private final Runnable unsubscriber;

    public BatteryLevelCharacteristic(Supplier<ListenableFuture<Integer>> getter,
                                      Consumer<HomekitCharacteristicChangeCallback> subscriber,
                                      Runnable unsubscriber) {
        super("00000068-0000-1000-8000-0026BB765291", false, true, "Battery Level", 0, 100, "%");
        this.getter = getter;
        this.subscriber = subscriber;
        this.unsubscriber = unsubscriber;
    }

    @Override
    protected ListenableFuture<Integer> getValue() {
        return getter.get();
    }

    @Override
    protected void setValue(Integer value) throws Exception {
        //Read Only
    }

    @Override
    public void subscribe(HomekitCharacteristicChangeCallback callback) {
        subscriber.accept(callback);
    }

    @Override
    public void unsubscribe() {
        unsubscriber.run();
    }
}
