package com.guok.hap;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author guok
 */

public abstract class AbstractBridge implements BridgeAuthInfo {
    protected String PIN;
    protected String mac;
    protected byte[] privateKey;
    protected final ConcurrentMap<String, byte[]> userKeyMap = new ConcurrentHashMap<>();


    public void initPairParams() {
        mac = initMac();
        privateKey = initPrivateKey();
        userKeyMap.putAll(initUsernamePublicKey());
    }
}
