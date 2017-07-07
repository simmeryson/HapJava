package com.guok.hap.accessories;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitAccessory;
import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.Service;
import com.guok.hap.impl.services.OutletService;

import java.util.Collection;
import java.util.Collections;

/**
 * A power outlet with boolean power and usage states.
 *
 * @author Andy Lintner
 */
public abstract class Outlet implements HomekitAccessory {

	@Override
	public Collection<Service> getServices() {
		return Collections.singleton((Service)new OutletService(this));
	}

	/**
	 * Retrieves the current binary state of the outlet's power.
	 * @return a future that will contain the binary state
	 */
	public abstract ListenableFuture<Boolean> getPowerState();
	
	/**
	 * Retrieves the current binary state indicating whether the outlet is in use.
	 * @return a future that will contain the binary state
	 */
	public abstract ListenableFuture<Boolean> getOutletInUse();
	
	/**
	 * Sets the binary state of the outlet's power.
	 * @param state the binary state to set
	 * @return a future that completes when the change is made
	 * @throws Exception when the change cannot be made
	 */
	public abstract ListenableFuture<Void> setPowerState(boolean state) throws Exception;
	
	/**
	 * Subscribes to changes in the binary state of the outlet's power.
	 * @param callback the function to call when the state changes.
	 */
	public abstract void subscribePowerState(HomekitCharacteristicChangeCallback callback);

	/**
	 * Subscribes to changes in the binary state indicating whether the outlet is in use.
	 * @param callback the function to call when the state changes.
	 */
	public abstract void subscribeOutletInUse(HomekitCharacteristicChangeCallback callback);
	
	/**
	 * Unsubscribes from changes in the binary state of the outlet's power.
	 */
	public abstract void unsubscribePowerState();

	/**
	 * Unsubscribes from changes in the binary state indicating whether hte outlet is in use.
	 */
	public abstract void unsubscribeOutletInUse();

}
