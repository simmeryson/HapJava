package com.guok.hap.impl.characteristics.smokesensor;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.accessories.SmokeSensor;
import com.guok.hap.accessories.properties.SmokeDetectedState;
import com.guok.hap.characteristics.EnumCharacteristic;
import com.guok.hap.characteristics.EventableCharacteristic;

public class SmokeDetectedCharacteristic extends EnumCharacteristic implements EventableCharacteristic {

    private final SmokeSensor smokeSensor;

    public SmokeDetectedCharacteristic(SmokeSensor smokeSensor) {
        super("00000076-0000-1000-8000-0026BB765291", false, true, "Smoke Detected", 1);
        this.smokeSensor = smokeSensor;
    }

    @Override
    protected ListenableFuture<Integer> getValue() {
        return Futures.transform(smokeSensor.getSmokeDetectedState(), new Function<SmokeDetectedState, Integer>() {
            @Override
            public Integer apply(SmokeDetectedState smokeDetectedState) {
                return smokeDetectedState.getCode();
            }
        });
    }

    @Override
    protected void setValue(Integer value) throws Exception {
        //Read Only
    }

    @Override
    public void subscribe(HomekitCharacteristicChangeCallback callback) {
        smokeSensor.subscribeSmokeDetectedState(callback);
    }

    @Override
    public void unsubscribe() {
        smokeSensor.unsubscribeSmokeDetectedState();
    }
}
