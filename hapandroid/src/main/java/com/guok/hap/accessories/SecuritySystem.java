package com.guok.hap.accessories;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitAccessory;
import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.Service;
import com.guok.hap.accessories.properties.CurrentSecuritySystemState;
import com.guok.hap.accessories.properties.SecuritySystemAlarmType;
import com.guok.hap.accessories.properties.TargetSecuritySystemState;
import com.guok.hap.impl.services.SecuritySystemService;

import java.util.Collection;
import java.util.Collections;

/**
 * <p>A security system that can be armed so that when a contact sensor is opened or a motion
 * sensor detects movement, then a siren could be fired off. There are different modes for arming
 * the system. See {@link TargetSecuritySystemState} for more information.</p>
 *
 * @author Gaston Dombiak
 */
public abstract class SecuritySystem implements HomekitAccessory {

    /**
     * Retrieves the current state of the security system. The state describes if the system
     * is armed in any of its variations; or if the alarm has been triggered; or if the system
     * is disarmed.
     *
     * @return current state of the security system.
     */
    public abstract ListenableFuture<CurrentSecuritySystemState> getCurrentSecuritySystemState();

    /**
     * Subscribes to changes to the state of the security system.
     *
     * @param callback the function to call when the state changes.
     */
    public abstract void subscribeCurrentSecuritySystemState(HomekitCharacteristicChangeCallback callback);

    /**
     * Unsubscribes from changes in the state of the security system.
     */
    public abstract void unsubscribeCurrentSecuritySystemState();

    /**
     * Sets the state of the security system. The security system could be armed in any
     * of its variations or disarmed.
     *
     * @return 0 when set successfully. {@link com.guok.hap.impl.responses.HapStatusCodes} when set failure.
     * @param state target state of the security system.
     * @throws Exception when the change cannot be made.
     */
    public abstract int setTargetSecuritySystemState(TargetSecuritySystemState state) throws Exception;

    /**
     * Retrieves the pending, but not yet completed, state of the security system.
     *
     * @return target state of the security system.
     */
    public abstract ListenableFuture<TargetSecuritySystemState> getTargetSecuritySystemState();

    /**
     * Subscribes to changes in the pending, but not yet completed, state of the security system.
     *
     * @param callback the function to call when the state changes.
     */
    public abstract void subscribeTargetSecuritySystemState(HomekitCharacteristicChangeCallback callback);

    /**
     * Unsubscribes from changes in the pending, but not yet completed, state of the security system.
     */
    public abstract void unsubscribeTargetSecuritySystemState();

    /**
     * Retrieves the alarm type of the security system.
     *
     * @return alarm type of the security system.
     */
    public abstract ListenableFuture<SecuritySystemAlarmType> getAlarmTypeState();

    /**
     * Subscribes to changes to the alarm type of the security system.
     *
     * @param callback the function to call when the alarm type changes.
     */
    public abstract void subscribeAlarmTypeState(HomekitCharacteristicChangeCallback callback);

    /**
     * Unsubscribes from changes in the alarm type of the security system.
     */
    public abstract void unsubscribeAlarmTypeState();

    @Override
    public Collection<Service> getServices() {
        return Collections.singleton((Service)new SecuritySystemService());
    }
}
