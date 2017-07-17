package com.guok.hap.impl.services;

import com.guok.hap.AccessoryDisplayInfo;
import com.guok.hap.HomekitAccessory;
import com.guok.hap.Service;

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
    protected final AccessoryDisplayInfo defaultDisplayInfo;

    public BaseAccessory(int ID, String label, Service service) {
        this.ID = ID;
        defaultDisplayInfo = new AccessoryDisplayInfo(label, "Manufacturer", "model", "SerialNumber", "1.0.0");
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
        return defaultDisplayInfo.getLabel();
    }

    @Override
    public void identify() {
        System.out.println("Accessory " + getLabel() + " identify!!");
    }

    @Override
    public String getSerialNumber() {
        return defaultDisplayInfo.getSerialNumber();
    }

    @Override
    public String getModel() {
        return defaultDisplayInfo.getModel();
    }

    @Override
    public String getManufacturer() {
        return defaultDisplayInfo.getManufacturer();
    }

    @Override
    public String getFirmwareRevision() {
        return defaultDisplayInfo.getFirmwareRevision();
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
