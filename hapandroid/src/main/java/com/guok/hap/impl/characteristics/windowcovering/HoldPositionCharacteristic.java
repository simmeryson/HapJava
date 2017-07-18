package com.guok.hap.impl.characteristics.windowcovering;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.guok.hap.characteristics.BooleanCharacteristic;
import com.guok.hap.characteristics.CharacteristicCallBack;

public class HoldPositionCharacteristic extends BooleanCharacteristic {

    //    private final WindowCovering windowCovering;
    public static final String UUID = "0000006F-0000-1000-8000-0026BB765291";

    public HoldPositionCharacteristic() {
        this(null);
    }

    public HoldPositionCharacteristic(CharacteristicCallBack<Boolean> callBack) {
        super(UUID, true, false, "Whether or not to hold position");

        this.mCallBack = callBack;
    }


    @Override
    public ListenableFuture<Boolean> getValue() {
        //Write only
        return Futures.immediateFuture(null);
    }

}
