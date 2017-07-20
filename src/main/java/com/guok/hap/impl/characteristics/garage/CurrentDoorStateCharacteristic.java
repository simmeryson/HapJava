package com.guok.hap.impl.characteristics.garage;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.EnumCharacteristic;
import com.guok.hap.characteristics.EventableCharacteristic;

public class CurrentDoorStateCharacteristic extends EnumCharacteristic implements EventableCharacteristic {

    public static final String UUID = "00000032-0000-1000-8000-0026BB765291";

    public CurrentDoorStateCharacteristic() {
        this(null);
    }

    public CurrentDoorStateCharacteristic(CharacteristicCallBack<Integer> callBack) {
        super(UUID, true, true, "Target Door State", 1);

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
