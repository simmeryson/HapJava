package com.guok.hap.accessories;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.accessories.properties.LockMechanismState;

/**
 * Extends {@link LockMechanism} with the ability to lock and unlock the mechanism.
 *
 * @author Andy Lintner
 */
public abstract class LockableLockMechanism extends LockMechanism {

	/**
	 * Sets the binary state of the lock mechanism.
	 *
	 * @return 0 when set successfully. {@link com.guok.hap.impl.responses.HapStatusCodes} when set failure.
	 * @param state true for a locked mechanism, false for unlocked.
	 * @throws Exception when the change cannot be made.
	 */
	public abstract int setTargetMechanismState(LockMechanismState state) throws Exception;

	/**
	 * Retrieves the pending, but not yet completed, state of the lock mechanism.
	 * @return the binary state
	 */
	public abstract ListenableFuture<LockMechanismState> getTargetMechanismState();

	/**
	 * Subscribes to changes in the pending, but not yet completed, binary state. 
	 * @param callback the function to call when the state changes.
	 */
	public abstract void subscribeTargetMechanismState(HomekitCharacteristicChangeCallback callback);

	/**
	 * Unsubscribes from changes in the pending, but not yet completed, binary state.
	 */
	public abstract void unsubscribeTargetMechanismState();
}
