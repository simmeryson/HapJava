package com.guok.hap;

import com.guok.hapandroid.ServiceConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author guok
 */

public abstract class AbstractBridge implements BridgeAuthInfo {

    private final static Logger logger = LoggerFactory.getLogger(AbstractBridge.class);

    protected String PIN;
    protected String mac;
    protected byte[] privateKey;
    protected final ConcurrentMap<String, byte[]> userKeyMap = new ConcurrentHashMap<>();

    protected final ServiceConfig mConfig;

    protected AbstractBridge(ServiceConfig config) {
        mConfig = config;
        PIN = mConfig.getPIN();
    }

    public void initPairParams() {
        PIN = mConfig.getPIN();
        mac = mConfig.getMac();
        privateKey = mConfig.getPrivateKey();
        userKeyMap.clear();
        userKeyMap.putAll(mConfig.getUsernamePublicKey());

        logger.info("The PIN for pairing is " + this.PIN);
        logger.info("The Port for http server is " + mConfig.getPort());
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
    public String getPIN() {
        return PIN;
    }

    @Override
    public int getPort() {
        return mConfig.getPort();
    }
}
