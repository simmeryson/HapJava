package com.guok.hap;


import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.accessories.Lightbulb;


/**
 * Created by guokai.
 */
public class MockSwitch extends Lightbulb {

    private boolean powerState = false;
    private HomekitCharacteristicChangeCallback subscribeCallback = null;

    final int id;
    final String label;
    final String serialNumber;
    final String manufacturer;
    final String model;

    public MockSwitch(int id, String label, String serialNumber, String manufacturer, String model) {
        this.id = id;
        this.label = label;
        this.serialNumber = serialNumber;
        this.manufacturer = manufacturer;
        this.model = model;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public void identify() {
        System.out.println("Identifying light");
    }

    @Override
    public String getSerialNumber() {
        return this.serialNumber;
    }

    @Override
    public String getModel() {
        return this.model;
    }

    @Override
    public String getManufacturer() {
        return this.manufacturer;
    }

    @Override
    public String getFirmwareRevision() {
        return "1.0.0";
    }

    @Override
    public ListenableFuture<Boolean> getLightbulbPowerState() {
        return Futures.immediateFuture(powerState);
    }

    @Override
    public ListenableFuture<Void> setLightbulbPowerState(boolean powerState)
            throws Exception {
        this.powerState = powerState;
        if (subscribeCallback != null) {
            subscribeCallback.changed();
        }
        System.out.println("The lightbulb is now "+(powerState ? "on" : "off"));
        return Futures.immediateFuture(null);
    }

    @Override
    public void subscribeLightbulbPowerState(
            HomekitCharacteristicChangeCallback callback) {
        this.subscribeCallback = callback;
    }

    @Override
    public void unsubscribeLightbulbPowerState() {
        this.subscribeCallback = null;
    }

}
