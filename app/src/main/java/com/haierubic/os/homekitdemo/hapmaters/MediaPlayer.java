package com.haierubic.os.homekitdemo.hapmaters;

import com.haierubic.os.homekitdemo.BroadcastCharactCallback;

/**
 * @author guok
 */

public class MediaPlayer extends BaseAccessory {

    public static final String TARGET = "player";

    public MediaPlayer() {
        super(4, "海尔播放器1", new Lightbulb("海尔播放器1"));


        Lightbulb lightbulb = getSpecificService(Lightbulb.class);
        lightbulb.addCharacteristic(new BrightnessCharact());

        lightbulb.getSpecificCharact(OnCharact.class).setCallBack(new BroadcastCharactCallback<Boolean>(TARGET, "power"));
        lightbulb.getSpecificCharact(BrightnessCharact.class).setCallBack(new BroadcastCharactCallback<Integer>(TARGET, "volume"));
    }
}
