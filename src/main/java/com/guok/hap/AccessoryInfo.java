package com.guok.hap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by guokai on 26/06/2017.
 */
public class AccessoryInfo {
    private String userName;
    private String displayName = "";
    private AccessoryCategory category = AccessoryCategory.OTHER;
    private String pincode = "";
    private int configVersion = 1;
    private String configHash = "";
    private final Map<String, String> pairedClients = new HashMap<>();// pairedClients[clientUsername:string] = clientPublicKey:Buffer

    public AccessoryInfo(String userName) {
        this.userName = userName;
    }

    public void addPairedClient(String userName, String publicKey) {
        this.pairedClients.put(userName, publicKey);
    }


    public void removePairedClient(String userName) {
        this.pairedClients.remove(userName);
    }

    public String getClientPublicKey(String userName) {
        return this.pairedClients.get(userName);
    }

    public boolean paired() {
        return pairedClients.size() > 0;
    }
}
