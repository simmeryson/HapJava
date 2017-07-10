package com.haierubic.os.homekitdemo.hapmaters;

import com.guok.hap.impl.characteristics.media.On;
import com.guok.hap.impl.services.Speaker;
import com.haierubic.os.homekitdemo.BroadcastCharactCallback;

/**
 * @author guok
 */

public class MediaPlayer extends BaseAccessory {

    public static final String TARGET = "player";

    public MediaPlayer() {
        super(4, "海尔播放器1", new Speaker("海尔播放器1"));

//        Speaker speaker = getSpecificService(Speaker.class);
//        VolumeCharacteristic volume = speaker.getSpecificCharact(VolumeCharacteristic.class);
//        volume.setCallBack(new BroadcastCharactCallback<Integer>(TARGET, "power"));

        getSpecificService(Speaker.class).getSpecificCharact(On.class).setCallBack(new BroadcastCharactCallback<Boolean>(TARGET, "power"));
    }

}
