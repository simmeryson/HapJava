package com.guok.hap;

import com.guok.hap.impl.advertiser.IAdvertiser;
import com.guok.hap.impl.advertiser.JmdnsHomekitAdvertiser;
import com.guok.hap.impl.characteristics.lightbulb.BrightnessCharacteristic;
import com.guok.hap.impl.characteristics.lightbulb.HueCharacteristic;
import com.guok.hap.impl.http.impl.HomeKitHttpServer;
import com.guok.hap.impl.services.LightbulbService;

import java.util.concurrent.ExecutionException;

/**
 * Created by guokai.
 */
public class MainTest {

    private static final int PORT = 9125;

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        try {
            AccessoryDisplayInfo displayInfo = new AccessoryDisplayInfo("Java bridge", "TestBridge, Inc.", "G6", "111abe234", "1.0.0");
            HomeKitHttpServer httpServer = new HomeKitHttpServer(PORT);
            IAdvertiser mAdvertiser = new JmdnsHomekitAdvertiser(httpServer.getInetAddress());
            HomeKitRoot bridge = new HomeKitRoot(displayInfo, httpServer, new MockAuthInfo(), mAdvertiser);

            bridge.addAccessory(new MockSwitch(2, "GKPlayer"));
            bridge.addAccessory(new MockSwitch(4, "GKPlayer")).getSpecificService(LightbulbService.class).addOptionalCharacteristic(new HueCharacteristic());
            bridge.getSpecificAccessory(2).getSpecificService(LightbulbService.UUID).addOptionalCharacteristic(new BrightnessCharacteristic());

            bridge.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
