package com.haierubic.os.homekitdemo;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

import com.guok.hap.AccessoryCategory;
import com.guok.hap.impl.advertiser.AbstractAdvertiser;

/**
 *
 */

public class AndroidAdvtiser extends AbstractAdvertiser {

    private NsdManager mNsdManager;
    private MDNSRegistrationListener mRegistrationListener;

    private volatile boolean isStart = false;

    public AndroidAdvtiser(Context context) {
        mNsdManager = (NsdManager) context.getApplicationContext().getSystemService(Context.NSD_SERVICE);
        mRegistrationListener = new MDNSRegistrationListener();

        discoverable = Boolean.valueOf(PreferencesUtil.getString(context, "IsPairing", "1"));
    }

    public void registerService() {
        NsdServiceInfo serviceInfo = new NsdServiceInfo();
        serviceInfo.setServiceName(label);
        serviceInfo.setServiceType(SERVICE_TYPE);
        serviceInfo.setPort(port);
        serviceInfo.setAttribute("sf", discoverable ? "1" : "0");
        serviceInfo.setAttribute("id", mac);
        serviceInfo.setAttribute("md", label);
        serviceInfo.setAttribute("c#", Integer.toString(configurationIndex));
        serviceInfo.setAttribute("s#", "1");
        serviceInfo.setAttribute("ci", AccessoryCategory.BRIDGE.getCode() + "");

        Log.i("GK", "sf:" + (discoverable ? "1" : "0") + "   id:" + mac);
        mNsdManager.registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD, mRegistrationListener);
    }

    @Override
    public synchronized void stop() {
        if (isStart){
            mNsdManager.unregisterService(mRegistrationListener);
        }
    }

    @Override
    public void setDiscoverable(boolean discoverable) {
        if (this.discoverable != discoverable) {
            this.discoverable = discoverable;
            if (isAdvertising) {
                logger.info("Re-creating service due to change in discoverability to " + discoverable);
                isAdvertising = false;
                stop();
            }
        }
    }

    @Override
    public void setConfigurationIndex(int revision) {
        if (this.configurationIndex != revision) {
            this.configurationIndex = revision;
            if (isAdvertising) {
                logger.info("Re-creating service due to change in configuration index to " + revision);
                isAdvertising = false;
                stop();
            }
        }

    }


    private final class MDNSRegistrationListener implements NsdManager.RegistrationListener {
        @Override
        public void onRegistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
            logger.info("onRegistrationFailed:" + serviceInfo.toString());
        }

        @Override
        public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
            logger.info("onUnregistrationFailed:" + serviceInfo.toString());
        }

        @Override
        public void onServiceRegistered(NsdServiceInfo serviceInfo) {
            isAdvertising = true;
            isReStart = false;
            isStart = true;
            logger.info("onServiceRegistered:" + serviceInfo.toString());
        }

        @Override
        public void onServiceUnregistered(NsdServiceInfo serviceInfo) {
            logger.info("onServiceUnregistered:" + serviceInfo.toString());
            if (!isAdvertising) registerService();
            isStart = false;
        }
    }
}
