package com.guok.hap.accessories;

import com.guok.hap.AccessoryDisplayInfo;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.impl.accessories.BaseAccessory;
import com.guok.hap.impl.characteristics.lock.mechanism.LockCurrentStateCharacteristic;
import com.guok.hap.impl.characteristics.lock.mechanism.LockTargetStateCharacteristic;
import com.guok.hap.impl.services.LockMechanismService;

/**
 * <p>A lock capable of exposing its binary locked state. For a lock that can be locked/unlocked, use
 * {@link LockableLockMechanism}.</p>
 * <p>
 * <p>Locks that run on batteries will need to implement this interface and also
 * implement {@link BatteryAccessory}.</p>
 *
 * @author guokai
 */
public class LockMechanism extends BaseAccessory {

    public LockMechanism(int ID, AccessoryDisplayInfo displayInfo) {
        super(ID, displayInfo, new LockMechanismService());
    }

    public LockMechanism(int ID, String label) {
        super(ID, label, new LockMechanismService());
    }

    public LockMechanism(int ID, AccessoryDisplayInfo displayInfo, String serviceName) {
        super(ID, displayInfo, new LockMechanismService(serviceName));
    }

    public LockMechanism(int ID, String label, String serviceName) {
        super(ID, label, new LockMechanismService(serviceName));
    }

    public LockMechanism setLockCurrentStateCallback(CharacteristicCallBack<Integer> callbask) {
        getSpecificService(LockMechanismService.class).
                getSpecificCharact(LockCurrentStateCharacteristic.class).setCallBack(callbask);
        return this;
    }

    public LockMechanism setLockTargetStateCallback(CharacteristicCallBack<Integer> callbask) {
        getSpecificService(LockMechanismService.class).
                getSpecificCharact(LockTargetStateCharacteristic.class).setCallBack(callbask);
        return this;
    }
}
