package com.guok.hap.impl.services;

import com.guok.hap.accessories.LockMechanism;
import com.guok.hap.accessories.LockableLockMechanism;
import com.guok.hap.impl.characteristics.lock.mechanism.CurrentLockMechanismStateCharacteristic;
import com.guok.hap.impl.characteristics.lock.mechanism.TargetLockMechanismStateCharacteristic;

public class LockMechanismService extends AbstractServiceImpl {

	public LockMechanismService(LockMechanism lock) {
		this(lock, lock.getLabel());
	}

	public LockMechanismService(LockMechanism lock, String serviceName) {
		super("00000045-0000-1000-8000-0026BB765291", lock, serviceName);
		addCharacteristic(new CurrentLockMechanismStateCharacteristic(lock));
		
		if (lock instanceof LockableLockMechanism) {
			addCharacteristic(new TargetLockMechanismStateCharacteristic((LockableLockMechanism) lock));
		}
	}
}
