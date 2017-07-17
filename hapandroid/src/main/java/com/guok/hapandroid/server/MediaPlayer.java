package com.guok.hapandroid.server;

import com.guok.hap.impl.characteristics.common.OnCharact;
import com.guok.hap.impl.characteristics.lightbulb.BrightnessCharact;
import com.guok.hap.impl.services.BaseAccessory;
import com.guok.hap.impl.services.LightbulbService;

/**
 * @author guok
 */

public class MediaPlayer extends BaseAccessory {

    public static final String TARGET = "player";

    public MediaPlayer() {
        super(4, "海尔播放器1", new LightbulbService("海尔播放器1"));

        LightbulbService lightbulbService = getSpecificService(LightbulbService.class);
        lightbulbService.addCharacteristic(new BrightnessCharact());

        lightbulbService.getSpecificCharact(OnCharact.class).setCallBack(new BroadcastCharactCallback<Boolean>(TARGET, "power"));
        lightbulbService.getSpecificCharact(BrightnessCharact.class).setCallBack(new BroadcastCharactCallback<Integer>(TARGET, "volume"));
    }
}
