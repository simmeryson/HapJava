package com.guok.hap.impl.characteristics.securitysystem;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.EnumCharacteristic;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.impl.responses.HapStatusCodes;

public class CurrentSecuritySystemStateCharacteristic extends EnumCharacteristic implements EventableCharacteristic {

    //    private final SecuritySystem securitySystem;
    public static final String UUID = "00000066-0000-1000-8000-0026BB765291";

    public CurrentSecuritySystemStateCharacteristic() {
        this(null);
    }

    public CurrentSecuritySystemStateCharacteristic(CharacteristicCallBack<Integer> callBack) {
        super(UUID, false, true, "Current security system state", 4);

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
