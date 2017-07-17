package com.guok.hap.accessories;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitAccessory;
import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.Service;
import com.guok.hap.impl.services.HumiditySensorService;

import java.util.Collection;
import java.util.Collections;

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
	public abstract ListenableFuture<Double> getCurrentRelativeHumidity();
	
	@Override
	public Collection<Service> getServices() {
		return Collections.singleton((Service)new HumiditySensorService());
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
