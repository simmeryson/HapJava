package com.guok.hap.impl.characteristics.carbonmonoxide;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.accessories.CarbonMonoxideSensor;
import com.guok.hap.accessories.properties.CarbonMonoxideDetectedState;
import com.guok.hap.characteristics.EnumCharacteristic;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.impl.responses.HapStatusCodes;

public class CarbonMonoxideDetectedCharacteristic extends EnumCharacteristic implements EventableCharacteristic {

    private final CarbonMonoxideSensor carbonMonoxideSensor;

    public CarbonMonoxideDetectedCharacteristic(CarbonMonoxideSensor carbonMonoxideSensor) {
        super("00000069-0000-1000-8000-0026BB765291", false, true, "Carbon Monoxide Detected", 1);
        this.carbonMonoxideSensor = carbonMonoxideSensor;
    }

    @Override
    protected ListenableFuture<Integer> getValue() {
        return Futures.transform(carbonMonoxideSensor.getCarbonMonoxideDetectedState(), new Function<CarbonMonoxideDetectedState, Integer>() {
            @Override
            public Integer apply(CarbonMonoxideDetectedState carbonMonoxideDetectedState) {
                return carbonMonoxideDetectedState.getCode();
            }
        });
    }

    @Override
    protected int setValue(Integer value) throws Exception {
        //Read Only
        return HapStatusCodes.READ_OLNY;
    }

    @Override
    public void subscribe(HomekitCharacteristicChangeCallback callback) {
        carbonMonoxideSensor.subscribeCarbonMonoxideDetectedState(callback);
    }

    @Override
    public void unsubscribe() {
        carbonMonoxideSensor.unsubscribeCarbonMonoxideDetectedState();
    }}
