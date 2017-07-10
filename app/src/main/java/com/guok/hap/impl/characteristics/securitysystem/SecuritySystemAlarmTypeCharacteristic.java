package com.guok.hap.impl.characteristics.securitysystem;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.accessories.SecuritySystem;
import com.guok.hap.accessories.properties.SecuritySystemAlarmType;
import com.guok.hap.characteristics.EnumCharacteristic;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.impl.responses.HapStatusCodes;

public class SecuritySystemAlarmTypeCharacteristic extends EnumCharacteristic implements EventableCharacteristic {

    private final SecuritySystem securitySystem;

    public SecuritySystemAlarmTypeCharacteristic(SecuritySystem securitySystem) {
        super("0000008E-0000-1000-8000-0026BB765291", false, true, "Security system alarm type", 1);
        this.securitySystem = securitySystem;
    }

    @Override
    public ListenableFuture<Integer> getValue() {
        return Futures.transform(securitySystem.getAlarmTypeState(), new Function<SecuritySystemAlarmType, Integer>() {
            @Override
            public Integer apply(SecuritySystemAlarmType securitySystemAlarmType) {
                return securitySystemAlarmType.getCode();
            }
        });
    }

    @Override
    protected int setValue(Integer value) throws Exception {
        //Not writable
        return HapStatusCodes.READ_OLNY;
    }

    @Override
    public void subscribe(HomekitCharacteristicChangeCallback callback) {
        securitySystem.subscribeAlarmTypeState(callback);
    }

    @Override
    public void unsubscribe() {
        securitySystem.unsubscribeAlarmTypeState();
    }
}
