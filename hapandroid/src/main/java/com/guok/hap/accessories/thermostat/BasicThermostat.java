package com.guok.hap.accessories.thermostat;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitAccessory;
import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.Service;
import com.guok.hap.accessories.TemperatureSensor;
import com.guok.hap.accessories.properties.ThermostatMode;
import com.guok.hap.impl.services.ThermostatService;

import java.util.Collection;
import java.util.Collections;

public abstract class BasicThermostat extends TemperatureSensor implements HomekitAccessory {

	/**
	 * Retrieves the current {@link ThermostatMode} of the thermostat.
	 * @return a future that will contain the mode.
	 */
	public abstract ListenableFuture<ThermostatMode> getCurrentMode();

	/**
	 * Subscribes to changes in the {@link ThermostatMode} of the thermostat.
	 * @param callback the function to call when the state changes.
	 */
	public abstract void subscribeCurrentMode(HomekitCharacteristicChangeCallback callback);

	/**
	 * Unsubscribes from changes in the mode of the thermostat.
	 */
	public abstract void unsubscribeCurrentMode();

	/**
	 * Sets the {@link ThermostatMode} of the thermostat.
	 * @param mode The {@link ThermostatMode} to set.
	 *
	 * @return 0 when set successfully. {@link com.guok.hap.impl.responses.HapStatusCodes} when set failure.
	 * @throws Exception when the change cannot be made.
	 */
	public abstract int setTargetMode(ThermostatMode mode) throws Exception;

	/**
	 * Retrieves the pending, but not yet complete, {@link ThermostatMode} of the thermostat.
	 * @return a future that will contain the target mode.
	 */
	public abstract ListenableFuture<ThermostatMode> getTargetMode();

	/**
	 * Subscribes to changes in the pending, but not yet complete, {@link ThermostatMode} of the thermostat.
	 * @param callback the function to call when the state changes.
	 */
	public abstract void subscribeTargetMode(HomekitCharacteristicChangeCallback callback);

	/**
	 * Unsubscribes from changes in the pending, but not yet complete, {@link ThermostatMode} of the thermostat.
	 */
	public abstract void unsubscribeTargetMode();

	/**
	 * Retrieves the target temperature, in celsius degrees.
	 * @return a future that will contain the target temperature.
	 */
	public abstract ListenableFuture<Double> getTargetTemperature();

	/**
	 * 	/**
	 * Sets the target temperature.
	 * @param value the target temperature, in celsius degrees.
	 * @return 0 when set successfully. {@link com.guok.hap.impl.responses.HapStatusCodes} when set failure.
	 * @throws Exception when the temperature cannot be changed.
	 */
	public abstract int setTargetTemperature(Double value) throws Exception;
	
	/**
	 * Subscribes to changes in the target temperature.
	 * @param callback the function to call when the state changes.
	 */
	public abstract void subscribeTargetTemperature(HomekitCharacteristicChangeCallback callback);

	/**
	 * Unsubscribes from changes in the target temperature.
	 */
	public abstract void unsubscribeTargetTemperature();
	
	@Override
	public Collection<Service> getServices() {
		return Collections.singleton((Service) new ThermostatService());
	}
}
