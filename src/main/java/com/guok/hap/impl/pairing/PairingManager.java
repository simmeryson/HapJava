package com.guok.hap.impl.pairing;

import com.guok.hap.BridgeAuthInfo;
import com.guok.hap.impl.HomekitRegistry;
import com.guok.hap.impl.advertiser.IAdvertiser;
import com.guok.hap.impl.http.HttpRequest;
import com.guok.hap.impl.http.HttpResponse;
import com.guok.hap.impl.responses.GeneralErrorResponse;
import com.guok.hap.impl.responses.HttpStatusCodes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * To establish a relationship between an iOS device and a HomeKit accessory, keys are
 * exchanged using Secure Remote Password (3072-bit) protocol, utilizing an eight-digit
 * code provided by the accessory’s manufacturer and entered on the iOS device by the
 * user  and then encrypted using ChaCha20-Poly1305 AEAD with HKDF-SHA-512 derived
 * keys. The accessory’s MFi certification is also verified during setup.
 * <p>
 * Pair Setup process follows the Secure Remote Password protocol.
 * This process assumes that the accessory is unpaired.
 * <p>Pair Setup is made of 6 steps, M1 to M6. Accessory needs to handle M1,M3,M5.
 * {@link SrpHandler} handle M1, M2. {@link FinalPairHandler} handle M5.
 */
public class PairingManager {

    private final static Logger logger = LoggerFactory.getLogger(PairingManager.class);

    private final BridgeAuthInfo authInfo;
    private final HomekitRegistry registry;
    private final IAdvertiser advertiser;

    private SrpHandler srpHandler;

    public PairingManager(BridgeAuthInfo authInfo, HomekitRegistry registry, IAdvertiser advertiser) {
        this.authInfo = authInfo;
        this.registry = registry;
        this.advertiser = advertiser;
    }

    public HttpResponse handle(HttpRequest httpRequest) throws Exception {
        PairSetupRequest req = PairSetupRequest.of(httpRequest.getBody());

        if (req.getStage() == Stage.ONE) {
            if (!advertiser.getDiscoverable()){
                String err = "bridge is already paired!";
                return TypeLengthValueUtils.createTLVErrorResponse(err, TLVState.M2.getKey(), TLVError.UNAVAILABLE);
            }//gk
            logger.info("Starting pair for " + registry.getLabel());
            srpHandler = new SrpHandler(authInfo.getPin());
            return srpHandler.handle(req);
        } else if (req.getStage() == Stage.TWO) {
            logger.info("Entering second stage of pair for " + registry.getLabel());
            if (srpHandler == null) {
                String err = "Received unexpected stage 2 request for " + registry.getLabel();
                return TypeLengthValueUtils.createTLVErrorResponse(err, TLVState.M4.getKey(), TLVError.AUTHENTICATION);
            } else {
                try {
                    return srpHandler.handle(req);
                } catch (Exception e) {
                    srpHandler = null; //You don't get to try again - need a new key
                    logger.error("Exception encountered while processing pairing request", e);
                    return TypeLengthValueUtils.createTLVErrorResponse(TLVState.M4.getKey(), TLVError.AUTHENTICATION);
                }
            }
        } else if (req.getStage() == Stage.THREE) {
            logger.info("Entering third stage of pair for " + registry.getLabel());
            if (srpHandler == null) {
                logger.error("Received unexpected stage 3 request for " + registry.getLabel());
                return TypeLengthValueUtils.createTLVErrorResponse(TLVState.M6.getKey(), TLVError.AUTHENTICATION);
            } else {
                FinalPairHandler handler = new FinalPairHandler(srpHandler.getK(), authInfo, advertiser);
                try {
                    return handler.handle(req);
                } catch (Exception e) {
                    logger.error("Exception while finalizing pairing", e);
                    return TypeLengthValueUtils.createTLVErrorResponse(TLVState.M6.getKey(), TLVError.AUTHENTICATION);
                }
            }
        }

        return new GeneralErrorResponse(HttpStatusCodes.NOT_FOUND);
    }
}
