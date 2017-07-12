package com.guok.hap.accessories;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitAccessory;
import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.Service;
import com.guok.hap.accessories.properties.SmokeDetectedState;
import com.guok.hap.impl.services.SmokeSensorService;

import java.util.Collection;
import java.util.Collections;

/**
 * <p>A smoke sensor reports whether smoke has been detected or not.</p>
 *
 * <p>Smoke sensors that run on batteries will need to implement this interface
 * and also implement {@link BatteryAccessory}.</p>
 *
 * @author Gaston Dombiak
 */
public abstract class SmokeSensor implements HomekitAccessory {

    /**
     * Retrieves the state of the smoke sensor. This is whether smoke has been detected or not.
     *
     * @return a future that will contain the smoke sensor's state
     */
    public abstract ListenableFuture<SmokeDetectedState> getSmokeDetectedState();

    @Override
    public Collection<Service> getServices() {
        return Collections.singleton((Service) new SmokeSensorService(this));
    }

    /**
     * Subscribes to changes in the smoke sensor's state.
     *
     * @param callback the function to call when the state changes.
     */
    public abstract void subscribeSmokeDetectedState(HomekitCharacteristicChangeCallback callback);

    /**
     * Unsubscribes from changes in the smoke sensor's state.
     */
    public abstract void unsubscribeSmokeDetectedState();
}
