package com.guok.hap.impl.characteristics.light;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.characteristics.CharacteristicUnits;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.characteristics.FloatCharacteristic;
import com.guok.hap.impl.responses.HapStatusCodes;

public class AmbientLightLevelCharacteristic extends FloatCharacteristic implements EventableCharacteristic {

    //    private final LightSensor lightSensor;
    public static final String UUID = "0000006B-0000-1000-8000-0026BB765291";

    public AmbientLightLevelCharacteristic() {
        this(null);
    }

    public AmbientLightLevelCharacteristic(CharacteristicCallBack<Double> callBack) {
        super(UUID, false, true, "Current ambient light level", 0.0001, 100000,
                0.0001, CharacteristicUnits.lux);
        this.mCallBack = callBack;
    }

    @Override
    protected int setValue(Double value) throws Exception {
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
