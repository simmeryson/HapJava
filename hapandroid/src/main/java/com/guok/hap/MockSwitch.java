package com.guok.hap;


import com.guok.hap.impl.accessories.BaseAccessory;
import com.guok.hap.impl.services.LightbulbService;


/**
 * Created by guokai.
 */
public class MockSwitch extends BaseAccessory {


    public MockSwitch(int ID, String label) {
        super(ID, label, new LightbulbService());
    }

    public MockSwitch(int ID, String label, String serviceName) {
        super(ID, label, new LightbulbService(serviceName));
    }

    public MockSwitch(int ID, String label, LightbulbService service) {
        super(ID, label, service);
    }
}
