package com.guok.hap.impl.characteristics.carbonmonoxide;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.accessories.CarbonMonoxideSensor;
import com.guok.hap.accessories.properties.CarbonMonoxideDetectedState;
import com.guok.hap.characteristics.EnumCharacteristic;
import com.guok.hap.characteristics.EventableCharacteristic;

import java.util.concurrent.CompletableFuture;

public class CarbonMonoxideDetectedCharacteristic extends EnumCharacteristic implements EventableCharacteristic {

    private final CarbonMonoxideSensor carbonMonoxideSensor;

    public CarbonMonoxideDetectedCharacteristic(CarbonMonoxideSensor carbonMonoxideSensor) {
        super("00000069-0000-1000-8000-0026BB765291", false, true, "Carbon Monoxide Detected", 1);
        this.carbonMonoxideSensor = carbonMonoxideSensor;
    }

    @Override
    protected CompletableFuture<Integer> getValue() {
        return carbonMonoxideSensor.getCarbonMonoxideDetectedState().thenApply(CarbonMonoxideDetectedState::getCode);
    }

    @Override
    protected void setValue(Integer value) throws Exception {
        //Read Only
    }

    @Override
    public void subscribe(HomekitCharacteristicChangeCallback callback) {
        carbonMonoxideSensor.subscribeCarbonMonoxideDetectedState(callback);
    }

    @Override
    public void unsubscribe() {
        carbonMonoxideSensor.unsubscribeCarbonMonoxideDetectedState();
    }}
