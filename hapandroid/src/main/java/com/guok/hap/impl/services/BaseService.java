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
 * all services require iOS 10 or 10.3 have not supported
 *
 * @author guok
 */

public abstract class BaseService implements Service {

    private final static Logger logger = LoggerFactory.getLogger(BaseService.class);

    protected final String type;
    protected final Map<String, Characteristic> characteristics = new ConcurrentHashMap<>();

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


    public <T extends Characteristic> T addCharacteristic(T characteristic) {
        if (characteristics.size() > 99) {
            logger.error("A service must not have more than 100 characteristics!");
            return null;
        }
        if (characteristics.containsKey(characteristic.getType())) {
            logger.error("Duplicate characteristic!");
            return null;
        }

        this.characteristics.put(characteristic.getType(), characteristic);
        return characteristic;
    }

    public <T extends Characteristic> T getSpecificCharact(Class<T> t) {
        for (Characteristic characteristic : characteristics.values()) {
            if (t.isInstance(characteristic))
                return (T) characteristic;
        }
        return null;
    }

    public void removeCharact(Characteristic characteristic) {
        this.characteristics.remove(characteristic.getType());
    }

    public <T extends Characteristic> void removeCharact(Class<T> t) {
        for (Map.Entry<String, Characteristic> entry : characteristics.entrySet()) {
            if (t.isInstance(entry.getValue())) {
                characteristics.remove(entry.getKey());
                break;
            }
        }
    }
}
