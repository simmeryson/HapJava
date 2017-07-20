package com.guok.hap.impl.characteristics.securitysystem;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.EnumCharacteristic;
import com.guok.hap.characteristics.EventableCharacteristic;

public class TargetSecuritySystemStateCharacteristic extends EnumCharacteristic implements EventableCharacteristic {

    //    private final SecuritySystem securitySystem;
    public static final String UUID = "00000067-0000-1000-8000-0026BB765291";

    public TargetSecuritySystemStateCharacteristic() {
        this(null);
    }

    public TargetSecuritySystemStateCharacteristic(CharacteristicCallBack<Integer> callBack) {
        super(UUID, true, true, "Target security system state", 3);

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
