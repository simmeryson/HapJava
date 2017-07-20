package com.guok.hap.impl.services;

import com.guok.hap.Service;
import com.guok.hap.characteristics.BaseCharacteristic;
import com.guok.hap.characteristics.Characteristic;
import com.guok.hap.impl.HomekitUtils;
import com.guok.hap.impl.characteristics.common.Name;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * all services requiring iOS 10 or 10.3 have not supported
 *
 * @author guok
 */

public abstract class BaseService implements Service {

    private final static Logger logger = LoggerFactory.getLogger(BaseService.class);

    protected final String type;
    protected final Map<String, Characteristic> requiredCharacteristics = new ConcurrentHashMap<>();
    protected final Map<String, Characteristic> optionalCharacteristics = new ConcurrentHashMap<>();

    public BaseService(String type) {
        this.type = HomekitUtils.getTypeFromUUID(type);
    }

    public BaseService(String type, String serviceName) {
        this(type);
        if (serviceName != null && serviceName.length() > 0)
            addCharacteristic(new Name(serviceName));
    }

    public Collection<Characteristic> getCharacteristics() {
        HashMap<String, Characteristic>  hashMap = new HashMap<>(requiredCharacteristics);
        hashMap.putAll(optionalCharacteristics);
        return Collections.unmodifiableCollection(hashMap.values());
    }

    @Override
    public String getType() {
        return type;
    }


    public <T extends Characteristic> T addCharacteristic(T characteristic) {
        if (requiredCharacteristics.size() + requiredCharacteristics.size() > 99) {
            logger.error("A service must not have more than 100 characteristics!");
            return null;
        }
        if (requiredCharacteristics.containsKey(characteristic.getType()) ||
                requiredCharacteristics.containsKey(characteristic.getType())) {
            logger.error("Duplicate characteristic!");
            return null;
        }

        this.requiredCharacteristics.put(characteristic.getType(), characteristic);
        return characteristic;
    }

    public <T extends Characteristic> T addOptionalCharacteristic(T characteristic) {
        if (optionalCharacteristics.size() + requiredCharacteristics.size() > 99) {
            logger.error("A service must not have more than 100 characteristics!");
            return null;
        }
        if (optionalCharacteristics.containsKey(characteristic.getType()) ||
                requiredCharacteristics.containsKey(characteristic.getType())) {
            logger.error("Duplicate characteristic!");
            return null;
        }

        this.optionalCharacteristics.put(characteristic.getType(), characteristic);
        return characteristic;
    }

    public <T extends Characteristic> T getSpecificCharact(Class<T> t) {
        for (Characteristic characteristic : requiredCharacteristics.values()) {
            if (t.isInstance(characteristic))
                return (T) characteristic;
        }

        for (Characteristic characteristic : optionalCharacteristics.values()) {
            if (t.isInstance(characteristic))
                return (T) characteristic;
        }
        return null;
    }

    public BaseCharacteristic getSpecificCharact(String UUID) {
        String charactType = HomekitUtils.getTypeFromUUID(UUID);
        if (charactType != null) {
            Characteristic characteristic = this.requiredCharacteristics.get(charactType);
            return (BaseCharacteristic) (characteristic != null ? characteristic : this.optionalCharacteristics.get(charactType));
        }
        return null;
    }

    public void removeCharacteristic(Characteristic characteristic) {
        Characteristic removed = this.optionalCharacteristics.remove(characteristic.getType());
        if (removed == null)
            this.optionalCharacteristics.remove(characteristic);
    }

    public <T extends Characteristic> void removeCharacteristic(Class<T> t) {
        for (Map.Entry<String, Characteristic> entry : requiredCharacteristics.entrySet()) {
            if (t.isInstance(entry.getValue())) {
                requiredCharacteristics.remove(entry.getKey());
                return;
            }
        }
        for (Map.Entry<String, Characteristic> entry : optionalCharacteristics.entrySet()) {
            if (t.isInstance(entry.getValue())) {
                optionalCharacteristics.remove(entry.getKey());
                return;
            }
        }
    }

    public void removeAllOptionalCharacteristic() {
        optionalCharacteristics.clear();
    }
}
