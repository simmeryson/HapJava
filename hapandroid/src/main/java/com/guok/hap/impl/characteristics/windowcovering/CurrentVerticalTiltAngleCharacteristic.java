package com.guok.hap.impl.characteristics.windowcovering;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.CharacteristicUnits;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.characteristics.IntegerCharacteristic;
import com.guok.hap.impl.responses.HapStatusCodes;

public class CurrentVerticalTiltAngleCharacteristic extends IntegerCharacteristic implements EventableCharacteristic {

    //    private final VerticalTiltingWindowCovering windowCovering;
    public static final String UUID = "0000006E-0000-1000-8000-0026BB765291";

    public CurrentVerticalTiltAngleCharacteristic() {
        this(null);
    }

    public CurrentVerticalTiltAngleCharacteristic(CharacteristicCallBack<Integer> callBack) {
        super(UUID, false, true, "The current vertical tilt angle", -90, 90, CharacteristicUnits.arcdegrees);

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
