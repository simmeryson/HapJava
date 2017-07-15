package com.guok.hapandroid.server;

import android.text.TextUtils;

import com.guok.hap.HomekitServer;
import com.guok.hap.ServiceConfig;
import com.guok.hapandroid.PreferencesUtil;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

import static com.guok.hapandroid.server.BridgeServer.DEFAULT_PIN;
import static com.guok.hapandroid.server.BridgeServer.DEFAULT_PORT;

/**
 * @author guok
 */

public class AndroidServiceConfigImpl implements ServiceConfig {
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
