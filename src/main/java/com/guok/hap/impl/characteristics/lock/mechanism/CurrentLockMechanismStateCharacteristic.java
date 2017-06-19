package com.guok.hap.impl.characteristics.lock.mechanism;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.accessories.LockMechanism;
import com.guok.hap.accessories.properties.LockMechanismState;
import com.guok.hap.characteristics.EnumCharacteristic;
import com.guok.hap.characteristics.EventableCharacteristic;

public class CurrentLockMechanismStateCharacteristic extends EnumCharacteristic implements EventableCharacteristic {

	private final LockMechanism lock;
	
	public CurrentLockMechanismStateCharacteristic(LockMechanism lock) {
		super("0000001D-0000-1000-8000-0026BB765291", false, true, "Current lock state", 3);
		this.lock = lock;
	}

	@Override
	protected void setValue(Integer value) throws Exception {
		//Not writable
	}

	@Override
	protected ListenableFuture<Integer> getValue() {
		return Futures.transform(lock.getCurrentMechanismState(), new Function<LockMechanismState, Integer>() {
			@Override
			public Integer apply(LockMechanismState lockMechanismState) {
				return lockMechanismState.getCode();
			}
		});
	}

	@Override
	public void subscribe(HomekitCharacteristicChangeCallback callback) {
		lock.subscribeCurrentMechanismState(callback);
	}

	@Override
	public void unsubscribe() {
		lock.unsubscribeCurrentMechanismState();
	}

}
