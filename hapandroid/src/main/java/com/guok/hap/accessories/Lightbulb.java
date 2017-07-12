package com.guok.hap.accessories;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitAccessory;
import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.Service;
import com.guok.hap.impl.services.LightbulbService;

import java.util.Collection;
import java.util.Collections;

/**
 * A simple light with a binary state.
 *
 * @author Andy Lintner
 */
public abstract class Lightbulb implements HomekitAccessory {

	/**
	 * Retrieves the current binary state of the light.
	 * @return a future that will contain the binary state
	 */
	public abstract ListenableFuture<Boolean> getLightbulbPowerState();
	
	/**
	 * Sets the binary state of the light
	 * @param powerState the binary state to set
	 * @return a future that completes when the change is made
	 * @throws Exception when the change cannot be made
	 */
	public abstract ListenableFuture<Void> setLightbulbPowerState(boolean powerState) throws Exception;
	
	@Override
	public Collection<Service> getServices() {
		return Collections.singleton((Service) new LightbulbService(this));
	}
	
	/**
	 * Subscribes to changes in the binary state of the light.
	 * @param callback the function to call when the state changes.
	 */
	public abstract void subscribeLightbulbPowerState(HomekitCharacteristicChangeCallback callback);
	
	/**
	 * Unsubscribes from changes in the binary state of the light.
	 */
	public abstract void unsubscribeLightbulbPowerState();
}
