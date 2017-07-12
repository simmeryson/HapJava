package com.guok.hap.accessories;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;


/**
 * Extends {@link Lightbulb} with color settings. This will usually be implemented along with 
 * {@link DimmableLightbulb}, but not necessarily so.
 *
 * @author Andy Lintner
 */
public abstract class ColorfulLightbulb extends Lightbulb {

	/**
	 * Retrieves the current hue of the light.
	 * 
	 * @return a future that will contain the hue, expressed in arc degrees from 0 to 360.
	 */
	public abstract ListenableFuture<Double> getHue();
	
	/**
	 * Sets the current hue of the light
	 * @param value the hue to set, expressed in arc degrees from 0 to 360.
	 * @return a future that completes when the hue is changed. Integer object means result;
	 * @throws Exception when the hue cannot be changed.
	 */
	public abstract ListenableFuture<Integer> setHue(Double value) throws Exception;
	
	/**
	 * Subscribes to changes in the hue of the light.
	 * @param callback the function to call when the state changes.
	 */
	public abstract void subscribeHue(HomekitCharacteristicChangeCallback callback);
	
	/**
	 * Unsubscribes from changes in the hue of the light.
	 */
	public abstract void unsubscribeHue();
	
	/**
	 * Retrieves the saturation of the light.
	 * 
	 * @return a future that will contain the saturation, expressed as a value between 0 and 100.
	 */
	public abstract ListenableFuture<Double> getSaturation();
	
	/**
	 * Sets the saturation of the light.
	 * @param value the saturation to set, expressed as a value between 0 and 100.
	 * @return a future that completes when the saturation is changed.Integer object means setting result.
	 * @throws Exception when the saturation cannot be set.
	 */
	public abstract ListenableFuture<Integer> setSaturation(Double value) throws Exception;

	/**
	 * Subscribes to changes in the saturation of the light.
	 * @param callback the function to call when the state changes.
	 */
	public abstract void subscribeSaturation(HomekitCharacteristicChangeCallback callback);

	/**
	 * Unsubscribes from changes in the saturation of the light.
	 */
	public abstract void unsubscribeSaturation();
	
}
