package com.guok.hap.impl.characteristics.lightbulb;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.CharacteristicUnits;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.characteristics.FloatCharacteristic;

/**
 * @author guok
 */

public class SaturationCharacteristic extends FloatCharacteristic implements EventableCharacteristic {

    public static final String UUID = "0000002F-0000-1000-8000-0026BB765291";

    public SaturationCharacteristic() {
        this(null);
    }

    public SaturationCharacteristic(CharacteristicCallBack<Double> callBack) {
        super(UUID, true, true, "Adjust saturation of the light", 0,
                100, 1, CharacteristicUnits.percentage);

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
