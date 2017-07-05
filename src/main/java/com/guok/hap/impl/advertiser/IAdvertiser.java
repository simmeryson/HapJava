package com.guok.hap.impl.advertiser;

/**
 * Created by guokai.
 */
public interface IAdvertiser {
    void advertise(String label, String mac, int port, int configurationIndex);
    void stop();
    void setDiscoverable(boolean discoverable);
    void setConfigurationIndex(int revision);
    boolean getDiscoverable();
    void setReStart(boolean reStart);
}
