package com.guok.hap.accessories;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitAccessory;
import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.Service;
import com.guok.hap.accessories.properties.WindowCoveringPositionState;
import com.guok.hap.impl.services.WindowCoveringService;

import java.util.Collection;
import java.util.Collections;

/**
 * A window covering, like blinds, which can be remotely controlled.
 *
 * @author Andy Lintner
 */
public abstract class WindowCovering implements HomekitAccessory {

	/**
	 * Retrieves the current position
	 * @return a future that will contain the position as a value between 0 and 100
	 */
	public abstract ListenableFuture<Integer> getCurrentPosition();
	
	/**
	 * Retrieves the target position
	 * @return a future that will contain the target position as a value between 0 and 100
	 */
	public abstract ListenableFuture<Integer> getTargetPosition();
	
	/**
	 * Retrieves the state of the position: increasing, decreasing, or stopped
	 * @return a future that will contain the current state
	 */
	public abstract ListenableFuture<WindowCoveringPositionState> getPositionState();
	
	/**
	 * Retrieves an indication that the window covering is obstructed from moving
	 * @return a future that will contain a boolean indicating whether an obstruction is present
	 */
	public abstract ListenableFuture<Boolean> getObstructionDetected();
	
	@Override
	public Collection<Service> getServices() {
		return Collections.singleton((Service)new WindowCoveringService(this));
	}
	
	/**
	 * Sets the target position
	 * @param position the target position to set, as a value between 1 and 100
	 * @return a future that completes when the change is made
	 * @throws Exception when the change cannot be made
	 */
	public abstract ListenableFuture<Integer> setTargetPosition(int position) throws Exception;
	
	/**
	 * Sets the hold position state
	 * @param hold whether or not to hold the current position state
	 * @return a future that completes when the change is made. Integer object means setting result
	 * @throws Exception when the change cannot be made
	 */
	public abstract ListenableFuture<Integer> setHoldPosition(boolean hold) throws Exception;
	
	/**
	 * Subscribes to changes in the current position.
	 * @param callback the function to call when the state changes.
	 */
	public abstract void subscribeCurrentPosition(HomekitCharacteristicChangeCallback callback);
	
	/**
	 * Subscribes to changes in the target position.
	 * @param callback the function to call when the state changes.
	 */
	public abstract void subscribeTargetPosition(HomekitCharacteristicChangeCallback callback);
	
	/**
	 * Subscribes to changes in the position state: increasing, decreasing, or stopped
	 * @param callback the function to call when the state changes.
	 */
	public abstract void subscribePositionState(HomekitCharacteristicChangeCallback callback);
	
	/**
	 * Subscribes to changes in the obstruction detected state
	 * @param callback the function to call when the state changes.
	 */
	public abstract void subscribeObstructionDetected(HomekitCharacteristicChangeCallback callback);

	/**
	 * Unsubscribes from changes in the current position.
	 */
	public abstract void unsubscribeCurrentPosition();
	
	/**
	 * Unsubscribes from changes in the target position.
	 */
	public abstract void unsubscribeTargetPosition();
	
	/**
	 * Unsubscribes from changes in the position state
	 */
	public abstract void unsubscribePositionState();
	
	/**
	 * Unsubscribes from changes in the obstruction detected state
	 */
	public abstract void unsubscribeObstructionDetected();
}
