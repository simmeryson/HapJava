package com.guok.hap.impl;

import com.guok.hap.HomekitAccessory;
import com.guok.hap.Service;
import com.guok.hap.characteristics.Characteristic;
import com.guok.hap.impl.accessories.BaseAccessory;
import com.guok.hap.impl.services.AccessoryInformationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Hold lists of accessories, and accessories's services and characteristics.
 */
public class HomekitRegistry {

    private final static Logger logger = LoggerFactory.getLogger(HomekitRegistry.class);

    private final String label;
    private final Map<Integer, HomekitAccessory> accessories;
    private final Map<HomekitAccessory, List<Service>> services = new HashMap<>();
    private final Map<HomekitAccessory, Map<Integer, Characteristic>> characteristics = new HashMap<>();
    private boolean isAllowUnauthenticatedRequests = false;

    public HomekitRegistry(String label) {
        this.label = label;
        this.accessories = new ConcurrentHashMap<>();
        reset();
    }

    /**
     * add services and characteristics from accessories
     */
    public synchronized void reset() {
        characteristics.clear();
        services.clear();
        for (HomekitAccessory accessory : accessories.values()) {
            int iid = 0;
            List<Service> newServices;
            try {
                newServices = new ArrayList<>();
                newServices.add(new AccessoryInformationService(accessory));
                newServices.addAll(accessory.getServices());
            } catch (Exception e) {
                logger.error("Could not instantiate services for accessory " + accessory.getLabel(), e);
                services.put(accessory, Collections.<Service>emptyList());
                continue;
            }
            services.put(accessory, newServices);

            Map<Integer, Characteristic> newCharacteristics = new HashMap<>();
            for (Service service : newServices) {
                iid++;
                for (Characteristic characteristic : service.getCharacteristics()) {
                    newCharacteristics.put(++iid, characteristic);
                }
            }
            characteristics.put(accessory, newCharacteristics);
        }
    }

    public String getLabel() {
        return label;
    }

    public Collection<HomekitAccessory> getAccessories() {
        return accessories.values();
    }

    public List<Service> getServices(Integer aid) {
        return Collections.unmodifiableList(services.get(accessories.get(aid)));
    }

    public Map<Integer, Characteristic> getCharacteristics(Integer aid) {
        Map<Integer, Characteristic> characteristics = this.characteristics.get(accessories.get(aid));
        if (characteristics == null) {
            return Collections.emptyMap();
        }
        return Collections.unmodifiableMap(characteristics);
    }

    public void add(HomekitAccessory accessory) {
        if (accessories.containsKey(accessory.getId()))
            logger.error("Duplicate accessory aid!");
        accessories.put(accessory.getId(), accessory);
    }

    public void remove(HomekitAccessory accessory) {
        accessories.remove(accessory.getId());
    }

    public boolean isAllowUnauthenticatedRequests() {
        return isAllowUnauthenticatedRequests;
    }

    public void setAllowUnauthenticatedRequests(boolean allow) {
        this.isAllowUnauthenticatedRequests = allow;
    }

    public BaseAccessory getSpecificAccessory(int id) {
        return (BaseAccessory) accessories.get(id);
    }
}
