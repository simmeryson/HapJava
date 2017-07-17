package com.guok.hap.accessories;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitAccessory;
import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.Service;
import com.guok.hap.impl.services.LightSensorService;

import java.util.Collection;
import java.util.Collections;

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
    public abstract ListenableFuture<Double> getCurrentAmbientLightLevel();

    @Override
    public Collection<Service> getServices() {
        return Collections.singleton((Service)new LightSensorService());
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
