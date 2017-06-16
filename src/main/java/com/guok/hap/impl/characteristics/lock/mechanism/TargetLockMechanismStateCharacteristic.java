package com.guok.hap.impl.characteristics.lock.mechanism;

import java.util.concurrent.CompletableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.accessories.LockableLockMechanism;
import com.guok.hap.accessories.properties.LockMechanismState;
import com.guok.hap.characteristics.EnumCharacteristic;
import com.guok.hap.characteristics.EventableCharacteristic;

public class TargetLockMechanismStateCharacteristic extends EnumCharacteristic implements EventableCharacteristic {

	private final LockableLockMechanism lock;
	
	public TargetLockMechanismStateCharacteristic(LockableLockMechanism lock) {
		super("0000001E-0000-1000-8000-0026BB765291", true, true, "Current lock state", 3);
		this.lock = lock;
	}

	@Override
	protected void setValue(Integer value) throws Exception {
		lock.setTargetMechanismState(LockMechanismState.fromCode(value));
	}

	@Override
	protected CompletableFuture<Integer> getValue() {
		return lock.getTargetMechanismState().thenApply(s -> s.getCode());
	}

	@Override
	public void subscribe(HomekitCharacteristicChangeCallback callback) {
		lock.subscribeTargetMechanismState(callback);
	}

	@Override
	public void unsubscribe() {
		lock.unsubscribeTargetMechanismState();
	}

}
