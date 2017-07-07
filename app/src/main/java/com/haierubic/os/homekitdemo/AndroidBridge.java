package com.haierubic.os.homekitdemo;

import android.content.Context;

import com.guok.hap.AbstractBridge;
import com.guok.hap.HomekitServer;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.util.Collections;
import java.util.Map;


public class AndroidBridge extends AbstractBridge {

    public static final String TAG = AndroidBridge.class.getSimpleName();

    private Context mContext;

    public AndroidBridge(Context context) throws InvalidAlgorithmParameterException {
        mContext = context.getApplicationContext();

        PIN = "031-45-155";

        initPairParams();

        System.out.println("Auth info is generated each time the sample application is started. Pairings are not persisted.");
        System.out.println("The PIN for pairing is " + PIN);
    }


    @Override
    public String getPin() {
        return PIN;
    }

    @Override
    public String getMac() {
        return mac;
    }

    @Override
    public byte[] getPrivateKey() {
        return privateKey;
    }

    @Override
    public void createUser(String iOSDevicePairingID, byte[] publicKey) {
        userKeyMap.putIfAbsent(iOSDevicePairingID, publicKey);
        System.out.println("Added pairing for " + iOSDevicePairingID);

        String s = new String(publicKey, StandardCharsets.ISO_8859_1);
        System.out.println("createUser " + iOSDevicePairingID + "   public key:" + publicKey.length);
        PreferencesUtil.putString(mContext, "Username", iOSDevicePairingID);
        PreferencesUtil.putString(mContext, "AccessoryLTPK", s);
        PreferencesUtil.putString(mContext, "IsPairing", "0");
    }

    @Override
    public void removeUser(String iOSDevicePairingID) {
        userKeyMap.remove(iOSDevicePairingID);
        System.out.println("Removed pairing for " + iOSDevicePairingID);

        PreferencesUtil.removeKey(mContext, "Username");
        PreferencesUtil.removeKey(mContext, "AccessoryLTPK");
        PreferencesUtil.putString(mContext, "IsPairing", "1");
    }

    @Override
    public byte[] getUserPublicKey(String iOSDevicePairingID) {
        return userKeyMap.get(iOSDevicePairingID);
    }

    @Override
    public boolean hasUser() {
        return userKeyMap.size() > 0;
    }

    @Override
    public String initMac() {
        String mac = PreferencesUtil.getString(mContext, "Mac");
        if (mac.length() == 0) {
            mac = HomekitServer.generateMac();
            PreferencesUtil.putString(mContext, "Mac", mac);
        }
        return mac;
    }

    @Override
    public byte[] initPrivateKey() {
        String ltsk = PreferencesUtil.getString(mContext, "AccessoryLTSK");
        byte[] key;
        if (ltsk.length() == 0) {
            key = HomekitServer.generateKey();
            PreferencesUtil.putString(mContext, "AccessoryLTSK", new String(key, StandardCharsets.ISO_8859_1));
        } else {
            key = ltsk.getBytes(StandardCharsets.ISO_8859_1);
        }
        return key;
    }

    @Override
    public Map<String, byte[]> initUsernamePublicKey() {
        String username = PreferencesUtil.getString(mContext, "Username");
        String accessoryLTPK = PreferencesUtil.getString(mContext, "AccessoryLTPK");
        if (username.length() > 0 && accessoryLTPK.length() > 0) {
            System.out.println("Added pairing for " + username + " public key:" + accessoryLTPK.getBytes(StandardCharsets.ISO_8859_1).length);
            return Collections.singletonMap(username, accessoryLTPK.getBytes(StandardCharsets.ISO_8859_1));
        }
        return Collections.emptyMap();
    }


    public void clearKeys(){
        PreferencesUtil.clear(mContext);
    }
}
