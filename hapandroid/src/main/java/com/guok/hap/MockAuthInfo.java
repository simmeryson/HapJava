package com.guok.hap; /**
 * Created by guokai on 15/06/2017.
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * This is a simple implementation that should never be used in actual production. The mac, salt, and privateKey
 * are being regenerated every time the application is started. The user store is also not persisted. This means pairing
 * needs to be re-done every time the app restarts.
 *
 * @author Andy Lintner
 */
public class MockAuthInfo implements BridgeAuthInfo {

    /**
     * must generate Setup Codes from a cryptographically secure random number generator
     */
    private static final String PIN = "031-45-153";

    private final String mac;
    private final byte[] privateKey;//AccessoryLTSK. for generate AccessoryLTPK
    private final ConcurrentMap<String, byte[]> userKeyMap = new ConcurrentHashMap<>();

    public MockAuthInfo() {
        mac = HomekitServer.generateMac();
        privateKey = HomekitServer.generateKey();
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
    }

    @Override
    public void removeUser(String iOSDevicePairingID) {
        userKeyMap.remove(iOSDevicePairingID);
        System.out.println("Removed pairing for " + iOSDevicePairingID);
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
    public void initPairParams() {

    }

    @Override
    public int getPort() {
        return 9125;
    }

    @Override
    public String getPIN() {
        return PIN;
    }

}
