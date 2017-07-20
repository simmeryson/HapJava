package com.guok.hap.impl.characteristics.carbonmonoxide;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.EnumCharacteristic;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.impl.responses.HapStatusCodes;

public class CarbonMonoxideDetectedCharacteristic extends EnumCharacteristic implements EventableCharacteristic {

    public static final String UUID = "00000069-0000-1000-8000-0026BB765291";

    public CarbonMonoxideDetectedCharacteristic() {
        this(null);
    }

    public CarbonMonoxideDetectedCharacteristic(CharacteristicCallBack<Integer> callBack) {
        super(UUID, false, true, "Carbon Monoxide Detected", 1);

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
