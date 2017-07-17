package com.guok.hap.impl.characteristics.lock.mechanism;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.EnumCharacteristic;
import com.guok.hap.characteristics.EventableCharacteristic;

public class LockTargetStateCharacteristic extends EnumCharacteristic implements EventableCharacteristic {

//	private final LockableLockMechanism lock;

    public LockTargetStateCharacteristic() {
        this(null);
    }

    public LockTargetStateCharacteristic(CharacteristicCallBack<Integer> callBack) {
        super("0000001E-0000-1000-8000-0026BB765291", true, true, "Current lock state", 3);
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
