package com.guok.hap.impl.characteristics.windowcovering;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.CharacteristicUnits;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.characteristics.IntegerCharacteristic;

public class TargetVerticalTiltAngleCharacteristic extends IntegerCharacteristic implements EventableCharacteristic {

//    private final VerticalTiltingWindowCovering windowCovering;

    public TargetVerticalTiltAngleCharacteristic() {
        this(null);
    }

    public TargetVerticalTiltAngleCharacteristic(CharacteristicCallBack<Integer> callBack) {
        super("0000007D-0000-1000-8000-0026BB765291", true, true, "The target vertical tilt angle", -90, 90, CharacteristicUnits.arcdegrees);
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
