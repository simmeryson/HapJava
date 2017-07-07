package com.guok.hap.accessories;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitAccessory;
import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.Service;
import com.guok.hap.accessories.properties.ContactState;
import com.guok.hap.impl.services.ContactSensorService;

import java.util.Collection;
import java.util.Collections;

/**
 * <p>A contact sensor that reports whether contact is detected or not. Typical
 * contact sensors are window/door sensors. When contact is detected it means
 * that the door/window is closed.</p>
 *
 * <p>Contact sensors that run on batteries will need to implement this interface
 * and also implement {@link BatteryAccessory}.</p>
 *
 * @author Gaston Dombiak
 */
public abstract class ContactSensor implements HomekitAccessory {

    /**
     * Retrieves the state of the contact. This is whether the contact is detected or not.
     * Detected contact means door/window is closed.
     *
     * @return a future that will contain the contact's state
     */
    public abstract ListenableFuture<ContactState> getCurrentState();

    @Override
    public Collection<Service> getServices() {
        return Collections.singleton((Service) new ContactSensorService(this));
    }

    /**
     * Subscribes to changes in the contact state.
     *
     * @param callback the function to call when the state changes.
     */
    public abstract void subscribeContactState(HomekitCharacteristicChangeCallback callback);

    /**
     * Unsubscribes from changes in the contact state.
     */
    public abstract void unsubscribeContactState();
}
