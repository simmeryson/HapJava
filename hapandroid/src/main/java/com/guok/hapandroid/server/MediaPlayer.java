package com.guok.hapandroid.server;

import com.guok.hap.impl.characteristics.common.OnCharacteristic;
import com.guok.hap.impl.characteristics.lightbulb.BrightnessCharacteristic;
import com.guok.hap.impl.accessories.BaseAccessory;
import com.guok.hap.impl.services.LightbulbService;

/**
 * @author guok
 */

public class MediaPlayer extends BaseAccessory {

    public static final String TARGET = "player";

    public MediaPlayer() {
        super(4, "海尔播放器1", new LightbulbService("海尔播放器1"));

        LightbulbService lightbulbService = getSpecificService(LightbulbService.class);
        lightbulbService.addCharacteristic(new BrightnessCharacteristic());

        lightbulbService.getSpecificCharact(OnCharacteristic.class).setCallBack(new BroadcastCharactCallback<Boolean>(TARGET, "power"));
        lightbulbService.getSpecificCharact(BrightnessCharacteristic.class).setCallBack(new BroadcastCharactCallback<Integer>(TARGET, "volume"));
    }
}
