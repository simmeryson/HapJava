package com.guok.hap.accessories;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import com.guok.hap.*;
import com.guok.hap.impl.services.HumiditySensorService;

/**
 * A humidity sensor that reports the current relative humidity.
 *
 * @author Andy Lintner
 */
public abstract class HumiditySensor implements HomekitAccessory {

	/**
	 * Retrieves the current relative humidity.
	 * @return a future that will contain the humidity as a value between 0 and 100
	 */
	CompletableFuture<Double> getCurrentRelativeHumidity();
	
	@Override
	public Collection<Service> getServices() {
		return Collections.singleton((Service)new HumiditySensorService(this));
	}
	
	/**
	 * Subscribes to changes in the current relative humidity.
	 * @param callback the function to call when the state changes.
	 */
	public abstract void subscribeCurrentRelativeHumidity(HomekitCharacteristicChangeCallback callback);

	/**
	 * Unsubscribes from changes in the current relative humidity.
	 */
	public abstract void unsubscribeCurrentRelativeHumidity();
	
}
