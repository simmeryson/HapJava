package com.guok.hap.impl.characteristics.windowcovering;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.CharacteristicUnits;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.characteristics.IntegerCharacteristic;
import com.guok.hap.impl.responses.HapStatusCodes;

public class CurrentPositionCharacteristic extends IntegerCharacteristic implements EventableCharacteristic {

    //	private final WindowCovering windowCovering;
    public static final String UUID = "0000006D-0000-1000-8000-0026BB765291";

    public CurrentPositionCharacteristic() {
        this(null);
    }

    public CurrentPositionCharacteristic(CharacteristicCallBack<Integer> callBack) {
        super(UUID, false, true, "The current position", 0, 100, CharacteristicUnits.percentage);
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
