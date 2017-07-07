package com.guok.hap.impl.services;

import com.guok.hap.Service;
import com.guok.hap.characteristics.Characteristic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author guok
 */

public abstract class BaseService implements Service{

    private final static Logger logger = LoggerFactory.getLogger(BaseService.class);

    private final String type;
    private final Map<String, Characteristic> characteristics = new ConcurrentHashMap<>();

    public BaseService(String type) {
        String s = Integer.toHexString(Integer.parseInt(type.split("-")[0], 16));
        this.type = s.toUpperCase();
    }

    @Override
    public Collection<Characteristic> getCharacteristics() {
        return Collections.unmodifiableCollection(characteristics.values());
    }

    @Override
    public String getType() {
        return type;
    }


    protected void addCharacteristic(Characteristic characteristic) {
        if (characteristics.size() > 99) {
            logger.error("A service must not have more than 100 characteristics!");
            return;
        }
        if (characteristics.containsKey(characteristic.getType())) {
            logger.error("Duplicate characteristic!");
            return;
        }

        this.characteristics.put(characteristic.getType(), characteristic);
    }
}
