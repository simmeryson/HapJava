package com.guok.hap.impl.characteristics.common;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.EnumCharacteristic;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.impl.responses.HapStatusCodes;

/**
 * Describing the service is active or not. 0 means inactive. 1 means active.
 * @author guok
 */

public class ActiveCharacteristic extends EnumCharacteristic implements EventableCharacteristic {

    public ActiveCharacteristic(String UUID, boolean isWritable, boolean isReadable, String description, int maxValue) {
        super("000000B0-0000-1000-8000-0026BB765291", true, true, "the service is active or not", 1);
    }

    @Override
    public void subscribe(HomekitCharacteristicChangeCallback callback) {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    protected int setValue(Integer value) throws Exception {
        this.value = value;
        return HapStatusCodes.SUCCESS;
    }

    @Override
    protected ListenableFuture<Integer> getValue() {
        return Futures.immediateFuture(value);
    }
}
