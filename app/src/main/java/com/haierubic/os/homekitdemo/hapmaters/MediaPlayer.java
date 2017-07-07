package com.haierubic.os.homekitdemo.hapmaters;

import com.guok.hap.impl.characteristics.media.VolumeCharacteristic;
import com.guok.hap.impl.services.Speaker;
import com.haierubic.os.homekitdemo.BroadcastCharactCallback;

/**
 * @author guok
 */

public class MediaPlayer extends BaseAccessory {

    public static final String TARGET = "player";

    public MediaPlayer() {
        super(2, "海尔播放器", new Speaker("123"));

        Speaker speaker = getSpecificService(Speaker.class);
        VolumeCharacteristic volume = speaker.getSpecificCharact(VolumeCharacteristic.class);
        volume.setCallBack(new BroadcastCharactCallback<Integer>(TARGET, "power"));
    }

}
