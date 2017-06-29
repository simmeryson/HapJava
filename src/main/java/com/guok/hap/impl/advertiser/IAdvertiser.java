package com.guok.hap.impl.advertiser;

/**
 * Created by guokai on 29/06/2017.
 */
public interface IAdvertiser {
    void advertise(String label, String mac, int port, int configurationIndex);
    void stop();
    void setDiscoverable(boolean discoverable);
    void setConfigurationIndex(int revision);
}
