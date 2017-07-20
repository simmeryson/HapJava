package com.guok.hap.accessories;

import com.guok.hap.AccessoryDisplayInfo;
import com.guok.hap.Service;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.impl.accessories.BaseAccessory;
import com.guok.hap.impl.characteristics.common.OnCharacteristic;
import com.guok.hap.impl.characteristics.outlet.OutletInUseCharacteristic;
import com.guok.hap.impl.services.OutletService;

import java.util.Collection;
import java.util.Collections;

/**
 * A power outlet with boolean power and usage states.
 *
 * @author guokai
 */
public class Outlet extends BaseAccessory {

    public Outlet(int ID, AccessoryDisplayInfo displayInfo) {
        super(ID, displayInfo, new OutletService());
    }

    public Outlet(int ID, String label) {
        super(ID, label, new OutletService());
    }

    public Outlet(int ID, AccessoryDisplayInfo displayInfo, String serviceName) {
        super(ID, displayInfo, new OutletService(serviceName));
    }

    public Outlet(int ID, String label, String serviceName) {
        super(ID, label, new OutletService(serviceName));
    }

    @Override
    public Collection<Service> getServices() {
        return Collections.singleton((Service) new OutletService());
    }

    public Outlet setOnCallback(CharacteristicCallBack<Boolean> callbask) {
        getSpecificService(OutletService.class).
                getSpecificCharact(OnCharacteristic.class).setCallBack(callbask);
        return this;
    }

    public Outlet setOutletInUseCallback(CharacteristicCallBack<Boolean> callbask) {
        getSpecificService(OutletService.class).
                getSpecificCharact(OutletInUseCharacteristic.class).setCallBack(callbask);
        return this;
    }
}
