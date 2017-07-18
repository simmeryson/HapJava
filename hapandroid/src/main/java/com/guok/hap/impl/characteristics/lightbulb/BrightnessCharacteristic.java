package com.guok.hap.impl.characteristics.lightbulb;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.CharacteristicUnits;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.characteristics.IntegerCharacteristic;

/**
 * @author guok
 */

public class BrightnessCharacteristic extends IntegerCharacteristic implements EventableCharacteristic {

    public static final String UUID = "00000008-0000-1000-8000-0026BB765291";

    public BrightnessCharacteristic() {
        this(null);
    }

    public BrightnessCharacteristic(CharacteristicCallBack<Integer> callBack) {
        super(UUID, true, true, "Adjust brightness of the light", 0,
                100, CharacteristicUnits.percentage);

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
