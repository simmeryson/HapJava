package com.guok.hap.accessories;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitAccessory;
import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.Service;
import com.guok.hap.impl.services.MotionSensorService;

import java.util.Collection;
import java.util.Collections;

/**
 * <p>A motion sensor that reports whether motion has been detected.</p>
 *
 * <p>Motion sensors that run on batteries will need to implement this interface
 * and also implement {@link BatteryAccessory}.</p>
 *
 * @author Gaston Dombiak
 */
public abstract class MotionSensor implements HomekitAccessory {

    /**
     * Retrieves the state of the motion sensor. If true then motion has been detected.
     *
     * @return a future that will contain the motion sensor's state
     */
    public abstract ListenableFuture<Boolean> getMotionDetected();

    @Override
    public Collection<Service> getServices() {
        return Collections.singleton((Service) new MotionSensorService());
    }

    /**
     * Subscribes to changes in the motion sensor.
     *
     * @param callback the function to call when the state changes.
     */
    public abstract void subscribeMotionDetected(HomekitCharacteristicChangeCallback callback);

    /**
     * Unsubscribes from changes in the motion sensor.
     */
    public abstract void unsubscribeMotionDetected();
}
