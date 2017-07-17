package com.guok.hap.impl.characteristics.media;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.CharacteristicUnits;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.characteristics.IntegerCharacteristic;

/**
 * @author guok
 */

public class VolumeCharacteristic extends IntegerCharacteristic implements EventableCharacteristic {

    public VolumeCharacteristic() {
        this(null);
    }


    public VolumeCharacteristic(CharacteristicCallBack<Integer> callBack) {
        super("00000119-0000-1000-8000-0026BB765291",
                true, true, "volume of audio", 0, 100, CharacteristicUnits.percentage);
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
