package com.guok.hap.accessories;

import com.guok.hap.HomekitAccessory;
import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.Service;
import com.guok.hap.impl.services.LightSensorService;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/**
 * A light sensor that reports current ambient light level.
 *
 * @author Gaston Dombiak
 */
public abstract class LightSensor implements HomekitAccessory {

    /**
     * Retrieves the current ambient light level.
     *
     * @return a future that will contain the luminance level expressed in LUX.
     */
    CompletableFuture<Double> getCurrentAmbientLightLevel();

    @Override
    public Collection<Service> getServices() {
        return Collections.singleton((Service)new LightSensorService(this));
    }

    /**
     * Subscribes to changes in the current ambient light level.
     *
     * @param callback the function to call when the state changes.
     */
    public abstract void subscribeCurrentAmbientLightLevel(HomekitCharacteristicChangeCallback callback);

    /**
     * Unsubscribes from changes in the current ambient light level.
     */
    public abstract void unsubscribeCurrentAmbientLightLevel();
}
