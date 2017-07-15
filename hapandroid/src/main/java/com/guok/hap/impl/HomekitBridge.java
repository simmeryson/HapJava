package com.guok.hap.impl;

import com.guok.hap.AccessoryDisplayInfo;
import com.guok.hap.AccessoryIdentify;
import com.guok.hap.impl.accessories.Bridge;
import com.guok.hap.Service;

import java.util.Collection;
import java.util.Collections;

/**
 * The Bridge Accessory. The instanceID must be 1;
 */
public class HomekitBridge implements Bridge {

    private final AccessoryDisplayInfo mDisplayInfo;
    private AccessoryIdentify mIdentify;

    public HomekitBridge(AccessoryDisplayInfo displayInfo) {
        mDisplayInfo = displayInfo;
    }

    public HomekitBridge(AccessoryDisplayInfo displayInfo, AccessoryIdentify identify) {
        this(displayInfo);
        mIdentify = identify;
    }

    @Override
    public String getLabel() {
        return mDisplayInfo.getLabel();
    }

    @Override
    public String getSerialNumber() {
        return mDisplayInfo.getSerialNumber();
    }

    @Override
    public String getModel() {
        return mDisplayInfo.getModel();
    }

    @Override
    public String getManufacturer() {
        return mDisplayInfo.getManufacturer();
    }

    @Override
    public String getFirmwareRevision() {
        return mDisplayInfo.getFirmwareRevision();
    }

    @Override
    public Collection<Service> getServices() {
        return Collections.emptyList();
    }

    @Override
    public int getId() {
        return 1;
    }

    @Override
    public void identify() {
        if (mIdentify != null)
            mIdentify.identify();
    }

    public AccessoryIdentify getIdentify() {
        return mIdentify;
    }

    public void setIdentify(AccessoryIdentify identify) {
        mIdentify = identify;
    }

    public AccessoryDisplayInfo getDisplayInfo() {
        return mDisplayInfo;
    }
}
