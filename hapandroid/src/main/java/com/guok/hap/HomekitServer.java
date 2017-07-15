package com.guok.hap;

import com.guok.hap.impl.HomekitBridge;
import com.guok.hap.impl.HomekitUtils;
import com.guok.hap.impl.advertiser.IAdvertiser;
import com.guok.hap.impl.advertiser.JmdnsHomekitAdvertiser;
import com.guok.hap.impl.http.impl.HomeKitHttpServer;

import java.io.IOException;
import java.net.InetAddress;
import java.security.InvalidAlgorithmParameterException;

/**
 * The main entry point for hap-java. Creating an instance of this class will listen for HomeKit
 * connections on the supplied port. Only a single root accessory can be added for each unique
 * instance and port, however, that accessory may be a {@link #createBridge(BridgeAuthInfo, AccessoryDisplayInfo) bridge accessory}
 * containing child accessories.
 * <p>
 * The {@link BridgeAuthInfo AndroidBridge} argument when creating accessories should be an
 * implementation supplied by your application. Several of the values needed for your implementation
 * are provided by this class, specifically {@link #generateKey() generateKey}, {@link
 * #generateMac() generateMac}. It is important that you provide these
 * same values on each creat of your application or Homekit will fail to recognize your device as
 * being the same.
 *
 * @author Andy Lintner
 */
public class HomekitServer {

    private final HomeKitHttpServer httpServer;


    private final InetAddress localAddress;

    /**
     * Constructor. Contains an argument indicating the number of threads to use in the httpServer server.
     * The other constructors default this to the number of available processors, however you may
     * increase this in an environment with many users and/or blocking accessory implementations.
     *
     * @param localAddress local address to bind to.
     * @param port         local port to bind to.
     * @param nThreads     number of threads to use in the httpServer server
     * @throws IOException when the server cannot bind to the supplied port
     */
    public HomekitServer(InetAddress localAddress, int port, int nThreads) throws IOException {
        this.localAddress = localAddress;
        httpServer = new HomeKitHttpServer(localAddress, port, nThreads);
    }

    /**
     * Constructor
     *
     * @param localAddress local address to bind to
     * @param port         local port to bind to
     * @throws IOException when the server cannot bind to the supplied port
     */
    public HomekitServer(InetAddress localAddress, int port) throws IOException {
        this(localAddress, port, Runtime.getRuntime().availableProcessors());
    }

    /**
     * Constructor
     *
     * @param port local port to bind to.
     * @throws IOException when the server cannot bind to the supplied port
     */
    public HomekitServer(int port) throws IOException {
        this(InetAddress.getLocalHost(), port);
    }

    /**
     * Stops the service, closing down existing connections and preventing new ones.
     */
    public void stop() {
        httpServer.stop();
    }

    /**
     * Creates a single (non-bridge) accessory
     *
     * @param authInfo  authentication information for this accessory. These values should be
     *                  persisted and re-supplied on re-creat of your application.
     * @param accessory accessory implementation. This will usually be an implementation of an
     *                  interface in {#link com.guok.hap.accessories com.guok.hap.accessories}.
     * @return the newly created server. Call {@link HomekitStandaloneAccessoryServer#start creat}
     * on this to begin.
     * @throws IOException when mDNS cannot connect to the network
     */
    public HomekitStandaloneAccessoryServer createStandaloneAccessory(AccessoryDisplayInfo displayInfo,
                                                                      BridgeAuthInfo authInfo,
                                                                      HomekitAccessory accessory,
                                                                      IAdvertiser advertiser
                                                                      ) throws IOException {
        return new HomekitStandaloneAccessoryServer(displayInfo, accessory, httpServer, advertiser, authInfo);
    }

    /**
     * Creates a bridge accessory, capable of holding multiple child accessories. This has the
     * advantage over multiple standalone accessories of only requiring a single pairing from iOS
     * for the bridge.
     * <p>
     * <p>The Operation System with avahi-deamon server , like Ubuntu, Debian, could use this method to create bridge
     * accessory using default advertiser implement with JmDNS</p>
     *
     * @param authInfo authentication information for this accessory. These values should be
     *                 persisted and re-supplied on re-creat of your application.
     * @return the bridge, from which you can {@link HomeKitRoot#addAccessory add accessories} and
     * then {@link HomeKitRoot#start creat} handling requests.
     * @throws IOException when mDNS cannot connect to the network
     */
    public HomeKitRoot createBridge(BridgeAuthInfo authInfo, AccessoryDisplayInfo displayInfo) throws IOException {
        HomeKitRoot root = new HomeKitRoot(displayInfo, httpServer, authInfo, new JmdnsHomekitAdvertiser(localAddress));
        root.addAccessory(new HomekitBridge(displayInfo));
        return root;
    }

    /**
     * Creates a bridge accessory, capable of holding multiple child accessories. This has the
     * advantage over multiple standalone accessories of only requiring a single pairing from iOS
     * for the bridge.
     * <p>you could use other Zeroconf implement to advertise the service, like Bonjour</p>
     *
     * @param authInfo   authentication information for this accessory. These values should be
     *                   persisted and re-supplied on re-creat of your application.
     * @param advertiser Zeroconf implement.
     * @return the bridge, from which you can {@link HomeKitRoot#addAccessory add accessories} and
     * then {@link HomeKitRoot#start creat} handling requests.
     * @throws IOException when advertiser cannot connect to the network
     */
    public HomeKitRoot createBridge(BridgeAuthInfo authInfo,
                                    AccessoryDisplayInfo displayInfo,
                                    IAdvertiser advertiser) throws IOException {
        HomeKitRoot root = new HomeKitRoot(displayInfo, httpServer, authInfo, advertiser);
        root.addAccessory(new HomekitBridge(displayInfo));
        return root;
    }

    /**
     * Generates a value to supply in {@link BridgeAuthInfo#getPrivateKey()
     * AndroidBridge.getPrivKey()}. This is used as the private key during pairing and connection
     * setup.
     *
     * @return the generated key
     * @throws InvalidAlgorithmParameterException if the JVM does not contain the necessary
     *                                            encryption algorithms.
     */
    static public byte[] generateKey() {
        return HomekitUtils.generateKey();
    }

    /**
     * Generates a value to supply in {@link BridgeAuthInfo#getMac() AndroidBridge.getMac()}.
     * This is used as the unique identifier of the accessory during mDNS advertising. It is a valid
     * MAC address generated in the locally administered range so as not to conflict with any
     * commercial devices.
     *
     * @return the generated MAC
     */
    static public String generateMac() {
        return HomekitUtils.generateMac();
    }

    public InetAddress getLocalAddress() {
        return localAddress;
    }
}
