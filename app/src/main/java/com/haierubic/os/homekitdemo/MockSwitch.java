package com.haierubic.os.homekitdemo;


import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.accessories.Lightbulb;
import com.guok.hap.characteristics.CharacteristicCallBack;


/**
 * @author guok
 */
public class MockSwitch extends Lightbulb {

    private boolean powerState = false;
    private HomekitCharacteristicChangeCallback subscribeCallback = null;

    CharacteristicCallBack<Boolean> mCallBack;

    public MockSwitch() {
        mCallBack = new BroadcastCharactCallback<>("player", "power");
    }

    @Override
    public int getId() {
        return 2;
    }

    @Override
    public String getLabel() {
        return "海尔播放器";
    }

    @Override
    public void identify() {
        System.out.println("Identifying light");
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

    @Override
    public ListenableFuture<Boolean> getLightbulbPowerState() {
        return Futures.immediateFuture(powerState);
    }

    @Override
    public ListenableFuture<Void> setLightbulbPowerState(boolean powerState)
            throws Exception {
        this.powerState = powerState;
        mCallBack.setValueCallback(powerState, subscribeCallback != null);
        if (subscribeCallback != null) {
            subscribeCallback.changed();
        }
        System.out.println("The lightbulb is now " + (powerState ? "on" : "off"));
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
