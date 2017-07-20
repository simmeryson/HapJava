package com.guok.hap.impl.characteristics.motionsensor;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.BooleanCharacteristic;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.impl.responses.HapStatusCodes;

public class MotionDetectedStateCharacteristic extends BooleanCharacteristic implements EventableCharacteristic {

    //    private final MotionSensor motionSensor;
    public static final String UUID = "00000022-0000-1000-8000-0026BB765291";

    public MotionDetectedStateCharacteristic() {
        this(null);
    }

    public MotionDetectedStateCharacteristic(CharacteristicCallBack<Boolean> callBack) {
        super(UUID, false, true, "Motion Detected");

        this.mCallBack = callBack;
    }


    @Override
    protected int setValue(Boolean value) throws Exception {
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
