package com.guok.hap.accessories.thermostat;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;

public abstract class HeatingThermostat extends BasicThermostat {

	/**
	 * Retrieves the temperature below which the thermostat should begin heating. 
	 * @return a future that will contain the threshold temperature, in celsius degrees. 
	 */
	public abstract ListenableFuture<Double> getHeatingThresholdTemperature();

	/**
	 * Sets the temperature below which the thermostat should begin heating. 
	 * @param value the threshold temperature, in celsius degrees.
	 * @throws Exception when the threshold temperature cannot be changed.
	 */
	public abstract void setHeatingThresholdTemperature(Double value) throws Exception;
	
	/**
	 * Subscribes to changes in the heating threshold.
	 * @param callback the function to call when the state changes.
	 */
	public abstract void subscribeHeatingThresholdTemperature(HomekitCharacteristicChangeCallback callback);

	/**
	 * Unsubscribes from changes in the heating threshold.
	 */
	public abstract void unsubscribeHeatingThresholdTemperature();
}
