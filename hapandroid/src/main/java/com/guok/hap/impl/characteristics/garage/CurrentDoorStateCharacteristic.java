package com.guok.hap.impl.characteristics.garage;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.EnumCharacteristic;
import com.guok.hap.characteristics.EventableCharacteristic;

public class CurrentDoorStateCharacteristic extends EnumCharacteristic implements EventableCharacteristic {

    //	private final GarageDoor door;

    public CurrentDoorStateCharacteristic() {
        this(null);
    }

    public CurrentDoorStateCharacteristic(CharacteristicCallBack<Integer> callBack) {
        super("00000032-0000-1000-8000-0026BB765291", true, true, "Target Door State", 1);

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
