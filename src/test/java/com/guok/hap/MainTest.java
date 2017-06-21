package com.guok.hap;

import java.util.concurrent.ExecutionException;

/**
 * Created by guokai on 21/06/2017.
 */
public class MainTest {

    private static final int PORT = 9123;

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        try {
            HomekitServer homekit = new HomekitServer(PORT);
            HomekitRoot bridge = homekit.createBridge(new MockAuthInfo(), "Test Bridge", "TestBridge, Inc.", "G6", "111abe234");
            bridge.addAccessory(new MockSwitch());
            bridge.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
