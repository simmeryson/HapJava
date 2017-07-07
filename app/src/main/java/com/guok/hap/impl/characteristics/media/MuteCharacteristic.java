package com.guok.hap.impl.characteristics.media;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.BooleanCharacteristic;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.impl.responses.HapStatusCodes;

/**
 * 0 means mute is on, or audio is on.
 * 1 means mute is off, or audio is off;
 *
 * @author guok
 */

public class MuteCharacteristic extends BooleanCharacteristic implements EventableCharacteristic {

    public MuteCharacteristic(CharacteristicCallBack<Boolean> callBack) {
        super("0000011A-0000-1000-8000-0026BB765291", true, true, "control of audio output");
        if (callBack != null)
            setCallBack(callBack);
    }

    public MuteCharacteristic() {
        this(null);
    }

    @Override
    public void subscribe(HomekitCharacteristicChangeCallback callback) {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    protected int setValue(Boolean value) throws Exception {
        this.value = value;
        if (this.mCallBack != null)
            return this.mCallBack.setValueCallback(value);
        return HapStatusCodes.SUCCESS;
    }

    @Override
    protected ListenableFuture<Boolean> getValue() {
        if (this.mCallBack != null)
            this.mCallBack.getValueCallback(value);
        return Futures.immediateFuture(value);
    }
}
