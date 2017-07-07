package com.haierubic.os.homekitdemo;

import android.content.Context;

import com.guok.hap.HomekitRoot;
import com.guok.hap.HomekitServer;
import com.guok.hap.impl.advertiser.IAdvertiser;

/**
 * @author guok
 */

public class BridgeImpl {
    private static int PORT = 9122;
    private HomekitRoot bridge;

    public void registerHap(Context context) {
        try {
            IAdvertiser mAdvertiser = new AndroidAdvtiser(context);
            HomekitServer homekit = new HomekitServer(PORT);
            bridge = homekit.createBridge(new AndroidBridge(context), "Android Bridge3", "Haier, Inc.", "G6", "111abe234", mAdvertiser);
            bridge.addAccessory(new MockSwitch(context));
            bridge.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearSP(Context context) {
        PreferencesUtil.clear(context);
    }

    public void reStart() {
        bridge.reStart();
    }

    public void stop() {
        bridge.stop();
    }
    public HomekitRoot getBridge() {
        return bridge;
    }
}
