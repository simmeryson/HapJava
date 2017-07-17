package com.guok.hap.accessories;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitAccessory;
import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.Service;
import com.guok.hap.accessories.properties.RotationDirection;
import com.guok.hap.impl.services.FanService;

import java.util.Collection;
import java.util.Collections;

/**
 * A fan, with power and rotational characteristics.
 *
 * @author Andy Lintner
 */
public abstract class Fan implements HomekitAccessory {

	/**
	 * Retrieves the current binary state of the fan's power.
	 * @return a future that will contain the binary state
	 */
	public abstract ListenableFuture<Boolean> getFanPower();

	/**
	 * Retrieves the current rotation direction of the fan.
	 * @return a future that will contain the direction
	 */
	public abstract ListenableFuture<RotationDirection> getRotationDirection();

	/**
	 * Retrieves the current speed of the fan's rotation
	 * @return a future that will contain the speed, expressed as an integer between 0 and 100.
	 */
	public abstract ListenableFuture<Integer> getRotationSpeed();
	
	/**
	 * Sets the binary state of the fan's power
	 * @param state the binary state to set
	 * @return a future that completes when the change is made
	 * @throws Exception when the change cannot be made
	 */
	public abstract ListenableFuture<Void> setFanPower(boolean state) throws Exception;
	
	/**
	 * Sets the rotation direction of the fan
	 * @param direction the direction to set
	 * @return a future that completes when the change is made. Integer object means setting result.
	 * @throws Exception when the change cannot be made
	 */
	public abstract ListenableFuture<Integer> setRotationDirection(RotationDirection direction) throws Exception;
	
	
	/**
	 * Sets the speed of the fan's rotation
	 * @param speed the speed to set, expressed as an integer between 0 and 100.
	 * @return a future that completes when the change is made. Integer object means setting result.
	 * @throws Exception when the change cannot be made
	 */
	public abstract ListenableFuture<Integer> setRotationSpeed(Integer speed) throws Exception;
	
	@Override
	public Collection<Service> getServices() {
		return Collections.singleton((Service)new FanService());
	}
	
	/**
	 * Subscribes to changes in the binary state of the fan's power.
	 * @param callback the function to call when the state changes.
	 */
	public abstract void subscribeFanPower(HomekitCharacteristicChangeCallback callback);
	
	/**
	 * Subscribes to changes in the rotation direction of the fan.
	 * @param callback the function to call when the direction changes.
	 */
	public abstract void subscribeRotationDirection(HomekitCharacteristicChangeCallback callback);
	
	/**
	 * Subscribes to changes in the rotation speed of the fan.
	 * @param callback the function to call when the speed changes.
	 */
	public abstract void subscribeRotationSpeed(HomekitCharacteristicChangeCallback callback);
	
	/**
	 * Unsubscribes from changes in the binary state of the fan's power.
	 */
	public abstract void unsubscribeFanPower();
	
	/**
	 * Unsubscribes from changes in the rotation direction of the fan.
	 */
	public abstract void unsubscribeRotationDirection();
	
	/**
	 * Unsubscribes from changes in the fan's rotation speed.
	 */
	public abstract void unsubscribeRotationSpeed();
}
