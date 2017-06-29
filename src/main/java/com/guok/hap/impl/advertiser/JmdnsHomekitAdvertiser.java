package com.guok.hap.impl.advertiser;

import com.guok.hap.AccessoryCategory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

public class JmdnsHomekitAdvertiser extends AbstractAdvertiser {

    private static final String SERVICE_TYPE = "_hap._tcp.local.";

    private final JmDNS jmdns;


    public JmdnsHomekitAdvertiser(InetAddress localAddress) throws UnknownHostException, IOException {
        jmdns = JmDNS.create(localAddress);
    }

    public synchronized void stop() {
        jmdns.unregisterAllServices();
    }

    public synchronized void setDiscoverable(boolean discoverable) {
        if (this.discoverable != discoverable) {
            this.discoverable = discoverable;
            if (isAdvertising) {
                logger.info("Re-creating service due to change in discoverability to " + discoverable);
                jmdns.unregisterAllServices();
                registerService();
            }
        }
    }

    public synchronized void setConfigurationIndex(int revision) {
        if (this.configurationIndex != revision) {
            this.configurationIndex = revision;
            if (isAdvertising) {
                logger.info("Re-creating service due to change in configuration index to " + revision);
                jmdns.unregisterAllServices();
                registerService();
            }
        }
    }

     public void registerService() {
        Map<String, String> props = new HashMap<>();
        props.put("sf", discoverable ? "1" : "0");
        props.put("id", mac);
        props.put("md", label);
        props.put("c#", Integer.toString(configurationIndex));
        props.put("s#", "1");
        props.put("ci", AccessoryCategory.BRIDGE.getCode()+"");
        logger.info("Registering " + SERVICE_TYPE + " " +
                "on port: " + port +
                " sf:" + props.get("sf") +
                " id:" + props.get("id") +
                " md:" + props.get("md") +
                " c#:" + props.get("c#") +
                " s#:" + props.get("s#") +
                " ci:" + props.get("ci")
        );
        try {
            jmdns.registerService(ServiceInfo.create(SERVICE_TYPE, label, port, 1, 1, props));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
