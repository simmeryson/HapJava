package com.guok.hapandroid.server;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.lang.ref.WeakReference;

/**
 * @author guok
 */

public class HapServerReveiver extends BroadcastReceiver {

    public static final String ACTION_HAP = "com.guok.HAPJAVA";

    private WeakReference<Service> serviceRef;

    public HapServerReveiver(Service serviceRef) {
        super();
        this.serviceRef = new WeakReference<Service>(serviceRef);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && ACTION_HAP.equals(intent.getAction())) {
            if (serviceRef != null) {
                BridgeServer bridge = ((HapMainService) serviceRef.get()).getBridge();
                if (bridge != null)
                    bridge.reStart();
            }
        }
    }
}
