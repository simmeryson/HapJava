package com.guok.hap.accessories;

import com.guok.hap.Service;
import com.guok.hap.impl.accessories.BaseAccessory;
import com.guok.hap.impl.services.FanService;

/**
 * A fan, with power and rotational characteristics.
 *
 * @author guok
 */
public class Fan extends BaseAccessory {

    public Fan(int ID, String label, Service service) {
        super(ID, label, service);
    }

    public Fan(int ID, String label) {
        super(ID, label, new FanService());
    }
}
