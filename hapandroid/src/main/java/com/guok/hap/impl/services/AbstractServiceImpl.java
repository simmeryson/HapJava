package com.guok.hap.impl.services;

import com.google.common.util.concurrent.ListenableFuture;
import com.guok.hap.HomekitAccessory;
import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.Service;
import com.guok.hap.accessories.BatteryAccessory;
import com.guok.hap.characteristics.Characteristic;
import com.guok.hap.impl.Consumer;
import com.guok.hap.impl.Supplier;
import com.guok.hap.impl.characteristics.common.BatteryLevelCharacteristic;
import com.guok.hap.impl.characteristics.common.Name;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

abstract class AbstractServiceImpl implements Service {

    private final static Logger logger = LoggerFactory.getLogger(AbstractServiceImpl.class);
    private final String type;
    private final Map<String, Characteristic> characteristics = new ConcurrentHashMap<>();

    /**
     * This constructor has been deprecated and replaced with
     * {@link #AbstractServiceImpl(String, HomekitAccessory, String)}. Usages of
     * this constructor will need to manually configure {@link Name} characteristic
     * and {@link BatteryLevelCharacteristic} if needed.
     *
     * @param type unique type of the service. This information can be obtained from HomeKit
     *             Accessory Simulator.
     */
    @Deprecated
    public AbstractServiceImpl(String type) {
        this(type, null, null);
    }

    /**
     * <p>Creates a new instance of this class with the specified type and {@link HomekitAccessory}.
     * Download and install <i>HomeKit Accessory Simulator</i> to discover the corresponding type
     * for the specific service.</p>
     *
     * <p>The new service will automatically add {@link Name} characteristic. If the accessory is
     * battery operated then it must implement {@link BatteryAccessory} and {@link
     * BatteryLevelCharacteristic} will be added too.</p>
     *
     * @param type        unique type of the service. This information can be obtained from HomeKit
     *                    Accessory Simulator.
     * @param accessory   HomeKit accessory exposed as a service.
     * @param serviceName name of the service. This information is usually the name of the
     *                    accessory.
     */
    public AbstractServiceImpl(String type, HomekitAccessory accessory, String serviceName) {
        String s = Integer.toHexString(Integer.parseInt(type.split("-")[0], 16));
        this.type = s.toUpperCase();

        if (accessory != null) {
            // Add name characteristic
            addCharacteristic(new Name(serviceName));

            // If battery operated accessory then add BatteryLevelCharacteristic
            if (accessory instanceof BatteryAccessory) {
                final BatteryAccessory batteryAccessory = (BatteryAccessory) accessory;
                addCharacteristic(new BatteryLevelCharacteristic(
                        new Supplier<ListenableFuture<Integer>>() {
                            @Override
                            public ListenableFuture<Integer> get() {
                                return batteryAccessory.getBatteryLevelState();
                            }
                        },
                        new Consumer<HomekitCharacteristicChangeCallback>() {
                            @Override
                            public void accept(HomekitCharacteristicChangeCallback homekitCharacteristicChangeCallback) {
                                batteryAccessory.subscribeBatteryLevelState(homekitCharacteristicChangeCallback);
                            }
                        },
                        new Runnable() {
                            @Override
                            public void run() {
                                batteryAccessory.unsubscribeBatteryLevelState();
                            }
                        }
                ));
            }
        }
    }

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
