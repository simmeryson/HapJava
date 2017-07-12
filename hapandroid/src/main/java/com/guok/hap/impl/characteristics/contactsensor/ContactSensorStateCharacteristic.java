package com.guok.hap.impl.characteristics.contactsensor;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.accessories.ContactSensor;
import com.guok.hap.accessories.properties.ContactState;
import com.guok.hap.characteristics.EnumCharacteristic;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.impl.responses.HapStatusCodes;

public class ContactSensorStateCharacteristic extends EnumCharacteristic implements EventableCharacteristic {

    private final ContactSensor contactSensor;

    public ContactSensorStateCharacteristic(ContactSensor contactSensor) {
        super("0000006A-0000-1000-8000-0026BB765291", false, true, "Contact State", 1);
        this.contactSensor = contactSensor;
    }

    @Override
    public ListenableFuture<Integer> getValue() {
        return Futures.transform(contactSensor.getCurrentState(), new Function<ContactState, Integer>() {
            @Override
            public Integer apply(ContactState contactState) {
                return contactState.getCode();
            }
        });
    }

    @Override
    protected int setValue(Integer value) throws Exception {
        //Read Only
        return HapStatusCodes.READ_OLNY;
    }

    @Override
    public void subscribe(HomekitCharacteristicChangeCallback callback) {
        contactSensor.subscribeContactState(callback);
    }

    @Override
    public void unsubscribe() {
        contactSensor.unsubscribeContactState();
    }
}
