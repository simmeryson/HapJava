package com.guok.hap.impl.advertiser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Interface of Zeroconf standard. Implement with Avahi or Bonjour.
 * <p>
 * <p>Bonjour Service needs TXT record, and TXT record as defined below </p>
 * <ul>
 * <li>"c#"
 * Current configuration number. Required.</li>
 * <li>"ff"         Feature flags, Required if
 * non-zero.</li>
 * <li>"id"         Device ID of accessory. Required. Also as the Pairing
 * Identifier.</li>
 * <li>"md"         Model name of accessory.Required.</li>
 * <li>"pv"
 * Protocol version string.Required if value is not 0.</li>
 * <li>"s#"         Current state
 * number.Required. it must be "1".</li>
 * <li>"sf"         Status flags.Value should be an unsigned
 * integer.Required if non-zero.</li>
 * <li>"ci"         Accessory Category Identifier.Required. This
 * must have range of 1-65535.This must take value defined in {@link
 * com.guok.hap.AccessoryCategory}</li>
 * </ul>
 * <p>
 * Created by guokai
 */
public abstract class AbstractAdvertiser implements IAdvertiser {
    protected static String SERVICE_TYPE = "_hap._tcp";

    protected volatile boolean discoverable = true;
    protected boolean isAdvertising = false;
    protected String label;
    protected String mac;
    protected int port;
    protected int configurationIndex;
    protected volatile boolean isReStart = false;

    protected final static Logger logger = LoggerFactory.getLogger(AbstractAdvertiser.class);

    public synchronized void advertise(String label, String mac, int port, int configurationIndex) {
        if (isAdvertising && !isReStart) {
            logger.error("Homekit IAdvertiser is already running!");
            return;
        }

        verifyServiceName(label);

        this.label = label;
        this.mac = mac;
        this.port = port;
        this.configurationIndex = configurationIndex;

        logger.info("Advertising accessory " + label);

        registerService();

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("Stopping advertising in response to shutdown.");
                stop();
            }
        }));
    }

    public abstract void registerService();

    public void verifyServiceName(String name) {
        if (name.length() > 63) {
            throw new IllegalArgumentException("the maximum length of service name is 63!");
        }
    }

    public boolean getDiscoverable() {
        return discoverable;
    }

    @Override
    public void setReStart(boolean reStart) {
        this.isReStart = reStart;
    }
}
