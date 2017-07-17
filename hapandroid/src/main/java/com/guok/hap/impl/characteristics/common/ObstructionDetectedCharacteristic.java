package com.guok.hap.impl.characteristics.common;


import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.BooleanCharacteristic;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.impl.responses.HapStatusCodes;


public class ObstructionDetectedCharacteristic extends BooleanCharacteristic implements EventableCharacteristic {

    public ObstructionDetectedCharacteristic() {
        this(null);
    }

    public ObstructionDetectedCharacteristic(CharacteristicCallBack<Boolean> callBack) {
        super("00000024-0000-1000-8000-0026BB765291", false, true, "An obstruction has been detected");

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
