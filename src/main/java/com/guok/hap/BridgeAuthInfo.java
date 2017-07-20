package com.guok.hap;

/**
 * refer the concatenate of AccessoryX, AccessoryPairingID, AccessoryLTPK.
 * Authentication info that must be provided when constructing a new {@link HomekitServer}. You will need to implement
 * this interface yourself to provide the necessary callbacks to a persistent storage mechanism. All values provided
 * must be constant across invocations or the accessories will require re-pairing within iOS.
 *
 * @author Andy Lintner
 */
public interface BridgeAuthInfo {

    /**
     * A pin code used for pairing the device. This pin will be required within iOS in order to complete pairing. The numbers
     * cannot be sequential and should not have a repeating pattern.
     * must generate Setup Codes from a cryptographically secure random number generator
     *
     * @return the pin code, in the form ###-##-###
     */
    String getPin();

    /**
     * A unique MAC address to be advertised with the Homekit information. This does not have to be the MAC address of the
     * network interface. You can generate this using {@link HomekitServer#generateMac()}.
     * Bonjour TXT record "id", Devices ID must persist across reboots. Also use as AccessoryPairingID in Pairing Setup step 3.
     *
     * @return the unique MAC address.
     */
    String getMac();


    /**
     * The private key used by the server during pairing and message encryption. You should generate this using
     * {@link HomekitServer#generateKey()}
     * AccessoryLTSK. for generate AccessoryLTPK in Pair Setup.
     *
     * @return the private key.
     */
    byte[] getPrivateKey();

    /**
     * Called during the pairing process, you should store the user and public key in a manner that the public key can later
     * be retrieved using {@link #getUserPublicKey(String)}. This must be stored in a persistent store as pairing will
     * need to be reset if the information is lost.
     *
     * @param iOSDevicePairingID the iOS device's username. The value will not be meaningful to anything but iOS. iOSDevicePairingID
     * @param publicKey          the iOS device's public key. iOSDeviceLTPK.
     */
    void createUser(String iOSDevicePairingID, byte[] publicKey);

    /**
     * Called when an iOS device needs to remove an existing pairing. Subsequent calls to {@link #getUserPublicKey(String)} for this
     * username return null.
     *
     * @param iOSDevicePairingID the username to delete from the persistent store.
     */
    void removeUser(String iOSDevicePairingID);

    /**
     * Called when an already paired iOS device is re-connecting. The public key returned by this method will be compared
     * with the signature of the pair verification request to validate the device.
     *
     * @param iOSDevicePairingID the username of the iOS device to retrieve the public key for.
     * @return the previously stored public key for this user. iOSDeviceLTPK.
     */
    byte[] getUserPublicKey(String iOSDevicePairingID);

    /**
     * Called to check if a user has been created. The homekit accessory advertises whether the accessory has already been paired.
     * At this time, it's unclear whether multiple users can be created, however it is known that advertising as unpaired
     * will break in iOS 9. The default value has been provided to maintain API compatibility for implementations targeting iOS 8.
     *
     * @return whether a user has been created and stored
     */
    boolean hasUser();

    void initPairParams();

    int getPort();

    String getPIN();
}
