package com.guok.hap.impl.characteristics.outlet;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.BooleanCharacteristic;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.impl.responses.HapStatusCodes;

public class OutletInUseCharacteristic extends BooleanCharacteristic implements EventableCharacteristic {

    //	private final Outlet outlet;
    public static final String UUID = "00000026-0000-1000-8000-0026BB765291";

    public OutletInUseCharacteristic() {
        this(null);
    }

    public OutletInUseCharacteristic(CharacteristicCallBack<Boolean> callBack) {
        super(UUID, false, true, "The outlet is in use");

        this.mCallBack = callBack;
    }

    @Override
    protected int setValue(Boolean value) throws Exception {
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
