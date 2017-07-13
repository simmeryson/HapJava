package com.guok.hapandroid;

import android.content.Context;
import android.text.TextUtils;

import com.guok.hap.HomekitRoot;
import com.guok.hap.HomekitServer;
import com.guok.hap.impl.advertiser.IAdvertiser;
import com.guok.hapandroid.hapmaters.MediaPlayer;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

/**
 * @author guok
 */

public class BridgeImpl {

    public static final String DEFAULT_PIN = "031-45-155";

    private static int DEFAULT_PORT = 7225;

    private HomekitRoot bridge;


    private final ServiceConfig mConfig;

    public BridgeImpl() {
        mConfig = new AndroidConfig();
    }

    public void registerHap(Context context) {
        try {
            IAdvertiser mAdvertiser = new AndroidAdvtiser(context);
            HomekitServer homekit = new HomekitServer(mConfig.getPort());
            bridge = homekit.createBridge(new AndroidBridge(context, mConfig), "Android Bridge3", "Haier, Inc.", "G6", "111abe234", mAdvertiser);
//            bridge.addAccessory(new MockSwitch());
            bridge.addAccessory(new MediaPlayer());
            bridge.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearSP(PreferencesUtil.NameSpace nameSpace) {
        PreferencesUtil.clear(nameSpace);
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


    class AndroidConfig implements ServiceConfig {


        @Override
        public String getPIN() {
            return PreferencesUtil.getString(PreferencesUtil.NameSpace.HapConfig, "PIN", DEFAULT_PIN);
        }

        @Override
        public boolean setPIN(String pin) {
            return !TextUtils.isEmpty(pin) && PreferencesUtil.putString(PreferencesUtil.NameSpace.HapConfig, "PIN", pin);
        }


        @Override
        public int getPort() {
            return PreferencesUtil.getInt(PreferencesUtil.NameSpace.HapConfig, "Port", DEFAULT_PORT);
        }

        @Override
        public boolean setPort(int port) {
            return port > -1 && port < 65536 && PreferencesUtil.putInt(PreferencesUtil.NameSpace.HapConfig, "Port", port);
        }


        @Override
        public String getMac() {
            String mac = PreferencesUtil.getString(PreferencesUtil.NameSpace.HapKeys, "Mac");
            if (mac.length() == 0) {
                mac = HomekitServer.generateMac();
                PreferencesUtil.putString(PreferencesUtil.NameSpace.HapKeys, "Mac", mac);
            }
            return mac;
        }

        @Override
        public byte[] getPrivateKey() {
            String ltsk = PreferencesUtil.getString(PreferencesUtil.NameSpace.HapKeys, "AccessoryLTSK");
            byte[] key;
            if (ltsk.length() == 0) {
                key = HomekitServer.generateKey();
                PreferencesUtil.putString(PreferencesUtil.NameSpace.HapKeys, "AccessoryLTSK", new String(key, StandardCharsets.ISO_8859_1));
            } else {
                key = ltsk.getBytes(StandardCharsets.ISO_8859_1);
            }
            return key;
        }

        @Override
        public Map getUsernamePublicKey() {
            String username = PreferencesUtil.getString(PreferencesUtil.NameSpace.HapKeys, "Username");
            String accessoryLTPK = PreferencesUtil.getString(PreferencesUtil.NameSpace.HapKeys, "AccessoryLTPK");
            if (username.length() > 0 && accessoryLTPK.length() > 0) {
                System.out.println("Added pairing for " + username + " public key:" + accessoryLTPK.getBytes(StandardCharsets.ISO_8859_1).length);
                return Collections.singletonMap(username, accessoryLTPK.getBytes(StandardCharsets.ISO_8859_1));
            }
            return Collections.emptyMap();
        }
    }
}
