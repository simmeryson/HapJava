package com.guok.hap.impl.characteristics.lightbulb;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.CharacteristicUnits;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.characteristics.FloatCharacteristic;

/**
 * @author guok
 */

public class HueCharact extends FloatCharacteristic implements EventableCharacteristic {

    public HueCharact() {
        this(null);
    }
    public HueCharact(CharacteristicCallBack<Double> callBack) {
        super("00000013-0000-1000-8000-0026BB765291", true, true, "Adjust hue of the light", 0, 360, 1, CharacteristicUnits.arcdegrees);

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
