package com.guok.hap.impl.pairing;

import com.guok.hap.HomekitAuthInfo;
import com.guok.hap.impl.advertiser.IAdvertiser;
import com.guok.hap.impl.http.HttpRequest;
import com.guok.hap.impl.http.HttpResponse;
import com.guok.hap.impl.pairing.TypeLengthValueUtils.DecodeResult;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Handle request with Uri of "/pairings" and Method of adding pairing ,remove pairing, list
 * pairing.
 */
public class PairingUpdateController {

    private final HomekitAuthInfo authInfo;
    private final IAdvertiser advertiser;

    public PairingUpdateController(HomekitAuthInfo authInfo, IAdvertiser advertiser) {
        this.authInfo = authInfo;
        this.advertiser = advertiser;
    }

    public HttpResponse handle(HttpRequest request) throws IOException {
        DecodeResult d = TypeLengthValueUtils.decode(request.getBody());

        int method = d.getByte(MessageType.METHOD);
        if (method == 3) { //Add pairing
            byte[] username = d.getBytes(MessageType.IDENTIFIER);
            byte[] ltpk = d.getBytes(MessageType.PUBLIC_KEY);
            if (username == null || username.length == 0 || ltpk == null || ltpk.length == 0) {
                return TypeLengthValueUtils.createTLVErrorResponse("IDENTIFIER is null!", (short) 2, TLVError.AUTHENTICATION);
            }
            authInfo.createUser(new String(username, StandardCharsets.UTF_8), ltpk);
        } else if (method == 4) { //Remove pairing
            byte[] username = d.getBytes(MessageType.IDENTIFIER);
            if (username == null || username.length == 0) {
                return TypeLengthValueUtils.createTLVErrorResponse("IDENTIFIER is null!", (short) 2, TLVError.AUTHENTICATION);
            } else {
                authInfo.removeUser(new String(username, StandardCharsets.UTF_8));
                if (!authInfo.hasUser()) {
                    advertiser.setDiscoverable(true);
                }
            }
        } else if (method == 5) { //Listing pairing

        } else {
            throw new RuntimeException("Unrecognized method: " + method);
        }
        return new PairingResponse(new byte[]{0x06, 0x01, 0x02});
    }


}
