package com.haierubic.os.homekitdemo.hapmaters;

import com.guok.hap.HomekitAccessory;
import com.guok.hap.Service;
import com.guok.hap.impl.services.BaseService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author guok
 */

public abstract class BaseAccessory implements HomekitAccessory{

    private final static Logger logger = LoggerFactory.getLogger(BaseService.class);

    private final Map<String, Service> services = new ConcurrentHashMap<>();
    private int ID;


    public BaseAccessory(int ID, Service service) {
        this.ID = ID;
        addServices(service);
    }

    protected void addServices(Service service) {
        if (services.size() > 99) {
            logger.error("A accessory must not have more than 100 services!");
            return;
        }
        if (services.containsKey(service.getType())) {
            logger.error("Duplicate service!");
            return;
        }

        this.services.put(service.getType(), service);
    }
}
