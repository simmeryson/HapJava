package com.guok.hap.impl.characteristics.securitysystem;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.accessories.SecuritySystem;
import com.guok.hap.accessories.properties.SecuritySystemAlarmType;
import com.guok.hap.characteristics.EnumCharacteristic;
import com.guok.hap.characteristics.EventableCharacteristic;

import java.util.concurrent.CompletableFuture;

public class SecuritySystemAlarmTypeCharacteristic extends EnumCharacteristic implements EventableCharacteristic {

    private final SecuritySystem securitySystem;

    public SecuritySystemAlarmTypeCharacteristic(SecuritySystem securitySystem) {
        super("0000008E-0000-1000-8000-0026BB765291", false, true, "Security system alarm type", 1);
        this.securitySystem = securitySystem;
    }

    @Override
    protected CompletableFuture<Integer> getValue() {
        return securitySystem.getAlarmTypeState().thenApply(SecuritySystemAlarmType::getCode);
    }

    @Override
    protected void setValue(Integer value) throws Exception {
        //Not writable
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
