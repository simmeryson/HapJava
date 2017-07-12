package com.guok.hapandroid.hapmaters;

import com.guok.hapandroid.BroadcastCharactCallback;

/**
 * @author guok
 */

public class MediaPlayer extends com.guok.hapandroid.hapmaters.BaseAccessory {

    public static final String TARGET = "player";

    public MediaPlayer() {
        super(4, "海尔播放器1", new LightbulbService("海尔播放器1"));

        LightbulbService lightbulbService = getSpecificService(LightbulbService.class);
        lightbulbService.addCharacteristic(new com.guok.hapandroid.hapmaters.BrightnessCharact());

        lightbulbService.getSpecificCharact(com.guok.hapandroid.hapmaters.OnCharact.class).setCallBack(new BroadcastCharactCallback<Boolean>(TARGET, "power"));
        lightbulbService.getSpecificCharact(com.guok.hapandroid.hapmaters.BrightnessCharact.class).setCallBack(new BroadcastCharactCallback<Integer>(TARGET, "volume"));
    }
}
