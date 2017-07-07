package com.guok.hap.accessories;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;

/**
 * Extends WindowCovering with the ability to control horizontal tilt angles
 *
 * @author Andy Lintner
 */
public abstract class HorizontalTiltingWindowCovering extends WindowCovering {

	/**
	 * Retrieves the current horizontal tilt angle
	 * @return a future that will contain the position as a value between -90 and 90
	 */
	public abstract ListenableFuture<Integer> getCurrentHorizontalTiltAngle();
	
	/**
	 * Retrieves the target horizontal tilt angle
	 * @return a future that will contain the target position as a value between -90 and 90
	 */
	public abstract ListenableFuture<Integer> getTargetHorizontalTiltAngle();
	
	/**
	 * Sets the target position
	 * @param angle the target angle to set, as a value between -90 and 90
	 * @return a future that completes when the change is made. Integer object means set result;
	 * @throws Exception when the change cannot be made
	 */
	public abstract ListenableFuture<Integer> setTargetHorizontalTiltAngle(int angle) throws Exception;
	
	/**
	 * Subscribes to changes in the current horizontal tilt angle.
	 * @param callback the function to call when the state changes.
	 */
	public abstract void subscribeCurrentHorizontalTiltAngle(HomekitCharacteristicChangeCallback callback);
	
	/**
	 * Subscribes to changes in the target horizontal tilt angle.
	 * @param callback the function to call when the state changes.
	 */
	public abstract void subscribeTargetHorizontalTiltAngle(HomekitCharacteristicChangeCallback callback);
	
	/**
	 * Unsubscribes from changes in the current horizontal tilt angle
	 */
	public abstract void unsubscribeCurrentHorizontalTiltAngle();
	
	/**
	 * Unsubscribes from changes in the target horizontal tilt angle
	 */
	public abstract void unsubscribeTargetHorizontalTiltAngle();
}
