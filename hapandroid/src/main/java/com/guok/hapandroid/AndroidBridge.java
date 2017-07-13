package com.guok.hapandroid;

import android.content.Context;

import com.guok.hap.AbstractBridge;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;


public class AndroidBridge extends AbstractBridge {

    public static final String TAG = AndroidBridge.class.getSimpleName();

    private Context mContext;


    public AndroidBridge(Context context, ServiceConfig config) throws InvalidAlgorithmParameterException {
        super(config);

        mContext = context.getApplicationContext();

        initPairParams();

        System.out.println("Auth info is generated each time the sample application is started. Pairings are not persisted.");
    }


    @Override
    public void createUser(String iOSDevicePairingID, byte[] publicKey) {
        userKeyMap.putIfAbsent(iOSDevicePairingID, publicKey);
        System.out.println("Added pairing for " + iOSDevicePairingID);

        String s = new String(publicKey, StandardCharsets.ISO_8859_1);
        System.out.println("createUser " + iOSDevicePairingID + "   public key:" + publicKey.length);
        PreferencesUtil.putString(PreferencesUtil.NameSpace.HapKeys, "Username", iOSDevicePairingID);
        PreferencesUtil.putString(PreferencesUtil.NameSpace.HapKeys, "AccessoryLTPK", s);
        PreferencesUtil.putString(PreferencesUtil.NameSpace.HapKeys, "IsPairing", "0");
    }

    @Override
    public void removeUser(String iOSDevicePairingID) {
        userKeyMap.remove(iOSDevicePairingID);
        System.out.println("Removed pairing for " + iOSDevicePairingID);

        PreferencesUtil.removeKey(PreferencesUtil.NameSpace.HapKeys, "Username");
        PreferencesUtil.removeKey(PreferencesUtil.NameSpace.HapKeys, "AccessoryLTPK");
        PreferencesUtil.putString(PreferencesUtil.NameSpace.HapKeys, "IsPairing", "1");
    }

    @Override
    public byte[] getUserPublicKey(String iOSDevicePairingID) {
        return userKeyMap.get(iOSDevicePairingID);
    }

    @Override
    public boolean hasUser() {
        return userKeyMap.size() > 0;
    }


    public void clearKeys(){
        PreferencesUtil.clear(PreferencesUtil.NameSpace.HapKeys);
    }
}
