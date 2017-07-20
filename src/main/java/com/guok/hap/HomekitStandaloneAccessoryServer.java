package com.guok.hap;

import com.guok.hap.impl.HomekitWebHandler;
import com.guok.hap.impl.advertiser.IAdvertiser;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * A server for exposing standalone Homekit accessory (as opposed to a Bridge accessory which
 * contains multiple accessories). Each standalone accessory will have its own pairing information,
 * port, and pin. Instantiate this class via {@link HomekitServer#createStandaloneAccessory(
 * AccessoryDisplayInfo, BridgeAuthInfo, HomekitAccessory)}
 *
 * @author Andy Lintner
 */
public class HomekitStandaloneAccessoryServer {

    private final HomeKitRoot root;

    HomekitStandaloneAccessoryServer(AccessoryDisplayInfo displayInfo,
                                     HomekitAccessory accessory,
                                     HomekitWebHandler webHandler,
                                     IAdvertiser iAdvertiser,
                                     BridgeAuthInfo authInfo) throws UnknownHostException, IOException {
        root = new HomeKitRoot(displayInfo, webHandler, authInfo, iAdvertiser);
        root.addAccessory(accessory);
    }

    /**
     * Begins advertising and handling requests for this accessory.
     */
    public void start() {
        root.start();
    }


}
