package com.guok.hap.accessories;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;

/**
 * Extends WindowCovering with the ability to control vertical tilt angles
 *
 * @author Andy Lintner
 */
public abstract class VerticalTiltingWindowCovering extends WindowCovering {

	/**
	 * Retrieves the current vertical tilt angle
	 * @return a future that will contain the position as a value between -90 and 90
	 */
	public abstract ListenableFuture<Integer> getCurrentVerticalTiltAngle();
	
	/**
	 * Retrieves the target vertical tilt angle
	 * @return a future that will contain the target position as a value between -90 and 90
	 */
	public abstract ListenableFuture<Integer> getTargetVerticalTiltAngle();
	
	/**
	 * Sets the target position
	 * @param angle the target angle to set, as a value between -90 and 90
	 * @return a future that completes when the change is made, Integer object means result.
	 * @throws Exception when the change cannot be made
	 */
	public abstract ListenableFuture<Integer> setTargetVerticalTiltAngle(int angle) throws Exception;
	
	/**
	 * Subscribes to changes in the current vertical tilt angle.
	 * @param callback the function to call when the state changes.
	 */
	public abstract void subscribeCurrentVerticalTiltAngle(HomekitCharacteristicChangeCallback callback);
	
	/**
	 * Subscribes to changes in the target vertical tilt angle.
	 * @param callback the function to call when the state changes.
	 */
	public abstract void subscribeTargetVerticalTiltAngle(HomekitCharacteristicChangeCallback callback);
	
	/**
	 * Unsubscribes from changes in the current vertical tilt angle
	 */
	public abstract void unsubscribeCurrentVerticalTiltAngle();
	
	/**
	 * Unsubscribes from changes in the target vertical tilt angle
	 */
	public abstract void unsubscribeTargetVerticalTiltAngle();
}
