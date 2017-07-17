package com.guok.hap.accessories;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitAccessory;
import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.Service;
import com.guok.hap.accessories.properties.TemperatureUnit;
import com.guok.hap.impl.services.TemperatureSensorService;

import java.util.Collection;
import java.util.Collections;

/**
 * A temperature sensor that reports the current temperature
 *
 * @author Andy Lintner
 */
public abstract class TemperatureSensor implements HomekitAccessory {

	/**
	 * Retrieves the current temperature, in celsius degrees.
	 * @return a future that will contain the temperature.
	 */
	public abstract ListenableFuture<Double> getCurrentTemperature();
	
	@Override
	public Collection<Service> getServices() {
		return Collections.singleton((Service) new TemperatureSensorService());
	}
	
	/**
	 * Subscribes to changes in the current temperature.
	 * @param callback the function to call when the state changes.
	 */
	public abstract void subscribeCurrentTemperature(HomekitCharacteristicChangeCallback callback);

	/**
	 * Unsubscribes from changes in the current temperature.
	 */
	public abstract void unsubscribeCurrentTemperature();
	
	/**
	 * Retrieves the minimum temperature, in celsius degrees, the thermostat can be set to.
	 * @return the minimum temperature.
	 */
	public abstract double getMinimumTemperature();

	/**
	 * Retrieves the maximum temperature, in celsius degrees, the thermostat can be set to.
	 * @return the maximum temperature.
	 */
	public abstract double getMaximumTemperature();
	
	/**
	 * Retrieves the temperature unit of the thermostat. The impact of this is unclear, as the actual temperature
	 * is always communicated in celsius degrees, and the iOS device uses the user's locale to determine
	 * the unit to convert to.
	 * @return the temperature unit of the thermostat.
	 */
	public TemperatureUnit getTemperatureUnit() { return TemperatureUnit.CELSIUS; }
}
