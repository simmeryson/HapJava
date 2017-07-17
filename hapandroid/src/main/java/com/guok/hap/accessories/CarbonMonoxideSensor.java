package com.guok.hap.accessories;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitAccessory;
import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.Service;
import com.guok.hap.accessories.properties.CarbonMonoxideDetectedState;
import com.guok.hap.impl.services.CarbonMonoxideSensorService;

import java.util.Collection;
import java.util.Collections;

/**
 * <p>A carbon monoxide sensor reports whether carbon monoxide has been detected or not.</p>
 *
 * <p>Carbon monoxide sensors that run on batteries will need to implement this interface
 * and also implement {@link BatteryAccessory}.</p>
 *
 * @author Gaston Dombiak
 */
public abstract class CarbonMonoxideSensor implements HomekitAccessory {

    /**
     * Retrieves the state of the sensor that indicates if carbon monoxide has been detected.
     *
     * @return a future that will contain the carbon monoxide sensor's state
     */
    public abstract ListenableFuture<CarbonMonoxideDetectedState> getCarbonMonoxideDetectedState();

    @Override
    public Collection<Service> getServices() {
        CarbonMonoxideSensorService carbonMonoxideSensorService = new CarbonMonoxideSensorService();
        return Collections.singleton((Service)carbonMonoxideSensorService);
    }

    /**
     * Subscribes to changes in the carbon monoxide's state.
     *
     * @param callback the function to call when the state changes.
     */
    public abstract void subscribeCarbonMonoxideDetectedState(HomekitCharacteristicChangeCallback callback);

    /**
     * Unsubscribes from changes in the carbon monoxide's state.
     */
    public abstract void unsubscribeCarbonMonoxideDetectedState();
}
