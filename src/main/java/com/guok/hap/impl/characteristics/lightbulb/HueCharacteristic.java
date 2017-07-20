package com.guok.hap.impl.characteristics.lightbulb;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.CharacteristicUnits;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.characteristics.FloatCharacteristic;

/**
 * @author guok
 */

public class HueCharacteristic extends FloatCharacteristic implements EventableCharacteristic {

    public static final String UUID = "00000013-0000-1000-8000-0026BB765291";

    public HueCharacteristic() {
        this(null);
    }

    public HueCharacteristic(CharacteristicCallBack<Double> callBack) {
        super(UUID, true, true, "Adjust hue of the light", 0, 360, 1, CharacteristicUnits.arcdegrees);

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
