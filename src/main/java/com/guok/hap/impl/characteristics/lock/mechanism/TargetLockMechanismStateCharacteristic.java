package com.guok.hap.impl.characteristics.lock.mechanism;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

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
	protected ListenableFuture<Integer> getValue() {
		return Futures.transform(lock.getTargetMechanismState(), new Function<LockMechanismState, Integer>() {
			@Override
			public Integer apply(LockMechanismState lockMechanismState) {
				return lockMechanismState.getCode();
			}
		});
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
