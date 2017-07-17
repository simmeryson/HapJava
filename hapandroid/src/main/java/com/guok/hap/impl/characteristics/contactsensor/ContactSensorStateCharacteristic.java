package com.guok.hap.impl.characteristics.contactsensor;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.EnumCharacteristic;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.impl.responses.HapStatusCodes;

public class ContactSensorStateCharacteristic extends EnumCharacteristic implements EventableCharacteristic {

//    private final ContactSensor contactSensor;

    public ContactSensorStateCharacteristic() {
        this(null);
    }

    public ContactSensorStateCharacteristic(CharacteristicCallBack<Integer> callBack) {
        super("0000006A-0000-1000-8000-0026BB765291", false, true, "Contact State", 1);

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
