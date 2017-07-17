package com.guok.hap.impl.characteristics.thermostat;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.CharacteristicUnits;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.characteristics.FloatCharacteristic;

public abstract class AbstractTemperatureCharacteristic extends FloatCharacteristic implements EventableCharacteristic {

    public AbstractTemperatureCharacteristic(String UUID, boolean isWritable, String description, double minValue, double maxValue) {
        super(UUID, isWritable, true, description, minValue, maxValue,
                0.1, CharacteristicUnits.celsius);
    }

    @Override
    public void subscribe(HomekitCharacteristicChangeCallback callback) {
        this.subcribeCallback = callback;
    }

    @Override
    public void unsubscribe() {
        this.subcribeCallback = null;
    }
}
