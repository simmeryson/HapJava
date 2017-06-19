package com.guok.hap.accessories.thermostat;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;

public abstract class CoolingThermostat extends BasicThermostat {

	/**
	 * Retrieves the temperature above which the thermostat should begin cooling.
	 * @return a future that will contain the threshold temperature, in celsius degrees.
	 */
	public abstract ListenableFuture<Double> getCoolingThresholdTemperature();

	/**
	 * Sets the temperature above which the thermostat should begin cooling. 
	 * @param value the threshold temperature, in celsius degrees.
	 * @throws Exception when the threshold temperature cannot be changed.
	 */
	public abstract void setCoolingThresholdTemperature(Double value) throws Exception;
	
	/**
	 * Subscribes to changes in the cooling threshold.
	 * @param callback the function to call when the state changes.
	 */
	public abstract void subscribeCoolingThresholdTemperature(HomekitCharacteristicChangeCallback callback);

	/**
	 * Unsubscribes from changes in the cooling threshold.
	 */
	public abstract void unsubscribeCoolingThresholdTemperature();
}
