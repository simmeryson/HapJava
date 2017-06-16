package com.guok.hap.accessories;

import com.guok.hap.HomekitAccessory;
import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.Service;
import com.guok.hap.accessories.properties.LockMechanismState;
import com.guok.hap.impl.services.LockMechanismService;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/**
 * <p>A lock capable of exposing its binary locked state. For a lock that can be locked/unlocked, use
 * {@link LockableLockMechanism}.</p>
 *
 * <p>Locks that run on batteries will need to implement this interface and also
 * implement {@link BatteryAccessory}.</p>
 *
 * @author Andy Lintner
 */
public interface LockMechanism extends HomekitAccessory {

	/**
	 * Retrieves the current binary state of the lock.
	 * @return a future that will contain the binary state.
	 */
	CompletableFuture<LockMechanismState> getCurrentMechanismState();

	/**
	 * Subscribes to changes in the binary state of the lock.
	 * @param callback the function to call when the state changes.
	 */
	void subscribeCurrentMechanismState(HomekitCharacteristicChangeCallback callback);

	/**
	 * Unsubscribes from changes in the binary state of the lock.
	 */
	void unsubscribeCurrentMechanismState();

	@Override
	default Collection<Service> getServices() {
		return Collections.singleton(new LockMechanismService(this));
	}
}
