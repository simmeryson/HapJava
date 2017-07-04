package com.guok.hap; /**
 * Created by guokai on 15/06/2017.
 */

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.util.Collections;
import java.util.Map;

/**
 * This is a simple implementation that should never be used in actual production. The mac, salt, and privateKey
 * are being regenerated every time the application is started. The user store is also not persisted. This means pairing
 * needs to be re-done every time the app restarts.
 *
 * @author Andy Lintner
 */
public class MockAuthInfo extends AbstractBridge {


    public MockAuthInfo() throws InvalidAlgorithmParameterException {
        PIN = "031-45-153";
        mac = initMac();
        salt = HomekitServer.generateSalt();
        privateKey = initPrivateKey();
        userKeyMap.putAll(initUsernamePublicKey());
        System.out.println("Auth info is generated each time the sample application is started. Pairings are not persisted.");
        System.out.println("The PIN for pairing is "+PIN);
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
    public BigInteger getSalt() {
        return salt;
    }

    @Override
    public byte[] getPrivateKey() {
        return privateKey;
    }

    @Override
    public void createUser(String username, byte[] publicKey) {
        userKeyMap.putIfAbsent(username, publicKey);
        System.out.println("Added pairing for "+username);
    }

    @Override
    public void removeUser(String username) {
        userKeyMap.remove(username);
        System.out.println("Removed pairing for "+username);
    }

    @Override
    public byte[] getUserPublicKey(String username) {
        return userKeyMap.get(username);
    }

    @Override
    public boolean hasUser() {
        return userKeyMap.size() > 0;
    }

    @Override
    public String initMac() {
        return HomekitServer.generateMac();
    }

    @Override
    public byte[] initPrivateKey() throws InvalidAlgorithmParameterException {
        return HomekitServer.generateKey();
    }

    @Override
    public Map initUsernamePublicKey() {
        return Collections.emptyMap();
    }

}
