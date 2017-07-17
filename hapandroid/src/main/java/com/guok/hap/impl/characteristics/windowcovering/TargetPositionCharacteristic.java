package com.guok.hap.impl.characteristics.windowcovering;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.CharacteristicUnits;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.characteristics.IntegerCharacteristic;

public class TargetPositionCharacteristic extends IntegerCharacteristic implements EventableCharacteristic {

//    private final WindowCovering windowCovering;

    public TargetPositionCharacteristic() {
        this(null);
    }

    public TargetPositionCharacteristic(CharacteristicCallBack<Integer> callBack) {
        super("0000007C-0000-1000-8000-0026BB765291", true, true, "The target position", 0, 100, CharacteristicUnits.percentage);
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
