package com.guok.hap;

import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author guok
 */

public abstract class AbstractBridge implements BridgeAuthInfo {
    protected String PIN;
    protected String mac;
    protected BigInteger salt;
    protected byte[] privateKey;
    protected final ConcurrentMap<String, byte[]> userKeyMap = new ConcurrentHashMap<>();

}
