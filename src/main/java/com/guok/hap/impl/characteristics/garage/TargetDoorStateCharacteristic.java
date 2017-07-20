package com.guok.hap.impl.characteristics.garage;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.EnumCharacteristic;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.impl.responses.HapStatusCodes;

public class TargetDoorStateCharacteristic extends EnumCharacteristic implements EventableCharacteristic {

    public static final String UUID = "0000000E-0000-1000-8000-0026BB765291";

    public TargetDoorStateCharacteristic() {
        this(null);
    }

    public TargetDoorStateCharacteristic(CharacteristicCallBack<Integer> callBack) {
        super(UUID, false, true, "Current Door State", 4);
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
