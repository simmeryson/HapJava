package com.guok.hap.impl.characteristics.smokesensor;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.EnumCharacteristic;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.impl.responses.HapStatusCodes;

public class SmokeDetectedCharacteristic extends EnumCharacteristic implements EventableCharacteristic {

    //    private final SmokeSensor smokeSensor;
    public static final String UUID = "00000076-0000-1000-8000-0026BB765291";

    public SmokeDetectedCharacteristic() {
        this(null);
    }

    public SmokeDetectedCharacteristic(CharacteristicCallBack<Integer> callBack) {
        super(UUID, false, true, "Smoke Detected", 1);

        this.mCallBack = callBack;
    }

    @Override
    protected int setValue(Integer value) throws Exception {
        //Read Only
        return HapStatusCodes.READ_OLNY;
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
