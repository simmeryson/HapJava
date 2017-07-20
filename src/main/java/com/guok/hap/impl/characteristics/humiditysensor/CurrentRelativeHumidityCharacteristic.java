package com.guok.hap.impl.characteristics.humiditysensor;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.CharacteristicUnits;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.characteristics.FloatCharacteristic;

public class CurrentRelativeHumidityCharacteristic extends FloatCharacteristic implements EventableCharacteristic {

//    private final HumiditySensor sensor;

    public static final String UUID = "00000010-0000-1000-8000-0026BB765291";

    public CurrentRelativeHumidityCharacteristic() {
        this(null);
    }

    public CurrentRelativeHumidityCharacteristic(CharacteristicCallBack<Double> callBack) {
        super(UUID, false, true, "Current relative humidity", 0, 100,
                0.1, CharacteristicUnits.percentage);
        this.mCallBack = callBack;
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
