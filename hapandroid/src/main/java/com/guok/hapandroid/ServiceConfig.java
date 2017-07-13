package com.guok.hapandroid;

import java.util.Map;

/**
 * @author guok
 */

public interface ServiceConfig {

    String getPIN();

    boolean setPIN(String pin);

    int getPort();

    boolean setPort(int port);

    /**
     * Bridge mac must be initially set by default. This should persist.
     */
    String getMac();

    /**
     * PrivateKey must be initially set by default. This should persist.
     */
    byte[] getPrivateKey();


    /**
     * Paired PublicKey and pairingID must be initially set by default. This should persist as pairs after pairSetup done.
     */
    Map getUsernamePublicKey();
}
