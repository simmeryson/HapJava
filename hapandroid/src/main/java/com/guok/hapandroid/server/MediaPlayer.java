package com.guok.hapandroid.server;

import com.guok.hap.AccessoryDisplayInfo;
import com.guok.hap.accessories.DimmableLightbulb;

/**
 * @author guok
 */

public class MediaPlayer extends DimmableLightbulb {

    public static final String TARGET = "player";
    public static final String OBJ_VOLUME = "volume";
    public static final String OBJ_POWER = "power";


    public MediaPlayer(int ID, String label) {
        super(ID, label);
    }

    public MediaPlayer(int ID, String label, String serviceName) {
        super(ID, label, serviceName);
    }

    public MediaPlayer(int ID, AccessoryDisplayInfo displayInfo, String serviceName) {
        super(ID, displayInfo, serviceName);
    }

    public MediaPlayer(int ID, AccessoryDisplayInfo displayInfo) {
        super(ID, displayInfo);
    }
}
