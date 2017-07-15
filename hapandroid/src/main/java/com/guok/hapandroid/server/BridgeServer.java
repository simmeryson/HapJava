package com.guok.hapandroid.server;

import android.content.Context;

import com.guok.hap.AccessoryDisplayInfo;
import com.guok.hap.DisplayConfig;
import com.guok.hap.HomeKitRoot;
import com.guok.hap.ServiceConfig;
import com.guok.hap.impl.advertiser.IAdvertiser;
import com.guok.hap.impl.http.impl.HomeKitHttpServer;
import com.guok.hapandroid.PreferencesUtil;
import com.guok.hapandroid.hapmaters.MediaPlayer;

/**
 * @author guok
 */

public class BridgeServer {

    public static final String DEFAULT_PIN = "031-45-155";
    public static int DEFAULT_PORT = 7225;

    private HomeKitRoot rootBridge;

    private static ServerPerpared mServerPerpared;
    private final ServiceConfig mServiceConfig;
    private final AccessoryDisplayInfo mDisplayInfo;


    public BridgeServer() {
        mServiceConfig = new AndroidServiceConfigImpl();
        mDisplayInfo = new AndroidDisplayConfig().getAccessoryDisplayInfo();
    }

    public BridgeServer(ServerPerpared serverPerpared) {
        this();
        mServerPerpared = serverPerpared;
    }

    public void registerHap(Context context) {
        try {
            IAdvertiser mAdvertiser = new AndroidAdvtiser(context);
            HomeKitHttpServer httpServer = new HomeKitHttpServer(mServiceConfig.getPort());
            rootBridge = new HomeKitRoot(mDisplayInfo, httpServer, new AndroidBridge(context, mServiceConfig), mAdvertiser);

            rootBridge.addAccessory(new MediaPlayer());

            if (mServerPerpared != null) {
                mServerPerpared.serverPerpared(rootBridge);
            }

            rootBridge.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearSP(PreferencesUtil.NameSpace nameSpace) {
        PreferencesUtil.clear(nameSpace);
    }

    public void reStart() {
        rootBridge.reStart();
    }

    public void stop() {
        rootBridge.stop();
    }

    public HomeKitRoot getRootBridge() {
        return rootBridge;
    }

    public static ServerPerpared getmServerPerpared() {
        return mServerPerpared;
    }

    public static void setmServerPerpared(ServerPerpared mServerPerpared) {
        BridgeServer.mServerPerpared = mServerPerpared;
    }

    class AndroidDisplayConfig implements DisplayConfig {

        @Override
        public AccessoryDisplayInfo getAccessoryDisplayInfo() {

            return new AccessoryDisplayInfo("Android Bridge", "GuoK Inc.", "model S", "12321", "1.0");
        }
    }
}
