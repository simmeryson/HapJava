package com.guok.hapandroid.hapmaters;

import com.guok.hap.HomekitAccessory;
import com.guok.hap.Service;
import com.guok.hap.impl.services.BaseService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author guok
 */

public abstract class BaseAccessory implements HomekitAccessory {

    private final static Logger logger = LoggerFactory.getLogger(BaseService.class);

    protected final Map<String, Service> services = new ConcurrentHashMap<>();
    protected final int ID;
    protected final String label;


    public BaseAccessory(int ID, String label, Service service) {
        this.ID = ID;
        this.label = label;
        addServices(service);
    }

    @Override
    public Collection<Service> getServices() {
        return Collections.unmodifiableCollection(services.values());
    }

    @Override
    public int getId() {
        return this.ID;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public void identify() {
        System.out.println("Accessory " + getLabel() + " identify!!");
    }

    @Override
    public String getSerialNumber() {
        return "none";
    }

    @Override
    public String getModel() {
        return "none";
    }

    @Override
    public String getManufacturer() {
        return "none";
    }

    @Override
    public String getFirmwareRevision() {
        return "1.0";
    }

    public <T extends Service> T addServices(T service) {
        if (services.size() > 99) {
            logger.error("A accessory must not have more than 100 services!");
            return null;
        }
        if (services.containsKey(service.getType())) {
            logger.error("Duplicate service!");
            return null;
        }

        this.services.put(service.getType(), service);
        return service;
    }

    public <T extends Service> T getSpecificService(Class<T> t) {
        for (Service service : services.values()) {
            if (t.isInstance(service))
                return (T) service;
        }
        return null;
    }

    public void removeService(Service service) {
        services.remove(service.getType());
    }
}
