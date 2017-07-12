package com.guok.hap.accessories;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitAccessory;
import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.Service;
import com.guok.hap.impl.services.SwitchService;

import java.util.Collection;
import java.util.Collections;

/**
 * A simple switch with a binary state.
 *
 * @author Andy Lintner
 */
public abstract class Switch implements  HomekitAccessory {

	/**
	 * Retrieves the current binary state of the switch.
	 * @return a future that will contain the binary state
	 */
	public abstract ListenableFuture<Boolean> getSwitchState();
	
	/**
	 * Sets the binary state of the switch
	 * @param state the binary state to set
	 * @return a future that completes when the change is made
	 * @throws Exception when the change cannot be made
	 */
	public abstract ListenableFuture<Void> setSwitchState(boolean state) throws Exception;
	
	@Override
	public Collection<Service> getServices() {
		return Collections.singleton((Service)new SwitchService(this));
	}
	
	/**
	 * Subscribes to changes in the binary state of the switch.
	 * @param callback the function to call when the state changes.
	 */
	public abstract void subscribeSwitchState(HomekitCharacteristicChangeCallback callback);
	
	/**
	 * Unsubscribes from changes in the binary state of the switch.
	 */
	public abstract void unsubscribeSwitchState();
}
