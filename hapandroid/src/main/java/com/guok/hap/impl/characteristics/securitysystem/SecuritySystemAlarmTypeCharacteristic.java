package com.guok.hap.impl.characteristics.securitysystem;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.EnumCharacteristic;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.impl.responses.HapStatusCodes;

public class SecuritySystemAlarmTypeCharacteristic extends EnumCharacteristic implements EventableCharacteristic {

    //    private final SecuritySystem securitySystem;
    public SecuritySystemAlarmTypeCharacteristic() {
        this(null);
    }

    public SecuritySystemAlarmTypeCharacteristic(CharacteristicCallBack<Integer> callBack) {
        super("0000008E-0000-1000-8000-0026BB765291", false, true, "Security system alarm type", 1);
        this.mCallBack = callBack;
    }


    @Override
    protected int setValue(Integer value) throws Exception {
        //Not writable
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
