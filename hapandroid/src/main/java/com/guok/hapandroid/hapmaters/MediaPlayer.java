package com.haierubic.os.homekitdemo.hapmaters;

import com.haierubic.os.homekitdemo.BroadcastCharactCallback;

/**
 * @author guok
 */

public class MediaPlayer extends com.haierubic.os.homekitdemo.hapmaters.BaseAccessory {

    public static final String TARGET = "player";

    public MediaPlayer() {
        super(4, "海尔播放器1", new LightbulbService("海尔播放器1"));

        LightbulbService lightbulbService = getSpecificService(LightbulbService.class);
        lightbulbService.addCharacteristic(new com.haierubic.os.homekitdemo.hapmaters.BrightnessCharact());

        lightbulbService.getSpecificCharact(com.haierubic.os.homekitdemo.hapmaters.OnCharact.class).setCallBack(new BroadcastCharactCallback<Boolean>(TARGET, "power"));
        lightbulbService.getSpecificCharact(com.haierubic.os.homekitdemo.hapmaters.BrightnessCharact.class).setCallBack(new BroadcastCharactCallback<Integer>(TARGET, "volume"));
    }
}
