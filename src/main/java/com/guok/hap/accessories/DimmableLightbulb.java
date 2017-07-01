package com.guok.hap.accessories;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;

import java.util.concurrent.CompletableFuture;

/**
 * Extends {@link Lightbulb} with brightness values.
 *
 * @author Andy Lintner
 */
public abstract class DimmableLightbulb extends Lightbulb {

	/**
	 * Retrieves the current brightness of the light
	 * @return a future that will contain the brightness, expressed as an integer between 0 and 100.
	 */
	public abstract ListenableFuture<Integer> getBrightness();
	
	/**
	 * Sets the current brightness of the light
	 * @param value the brightness, on a scale of 0 to 100, to set
	 * @return a future that completes when the brightness is changed. Integer object means set result;
	 * @throws Exception when the brightness cannot be set
	 */
	public abstract ListenableFuture<Integer> setBrightness(Integer value) throws Exception;
	
	/**
	 * Subscribes to changes in the brightness of the light.
	 * @param callback the function to call when the state changes.
	 */
	public abstract void subscribeBrightness(HomekitCharacteristicChangeCallback callback);
	
	/**
	 * Unsubscribes from changes in the brightness of the light.
	 */
	public abstract void unsubscribeBrightness();
	
}
