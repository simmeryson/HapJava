package com.guok.hap.impl.pairing;

import com.guok.hap.HomekitAuthInfo;
import com.guok.hap.impl.HomekitRegistry;
import com.guok.hap.impl.advertiser.IAdvertiser;
import com.guok.hap.impl.http.HttpRequest;
import com.guok.hap.impl.http.HttpResponse;
import com.guok.hap.impl.responses.NotFoundResponse;
import com.guok.hap.impl.responses.UnauthorizedResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PairingManager {

	private final static Logger logger = LoggerFactory.getLogger(PairingManager.class);
	
	private final HomekitAuthInfo authInfo;
	private final HomekitRegistry registry;
	private final IAdvertiser advertiser;
	
	private SrpHandler srpHandler;
	
	public PairingManager(HomekitAuthInfo authInfo, HomekitRegistry registry, IAdvertiser advertiser) {
		this.authInfo = authInfo;
		this.registry = registry;
		this.advertiser = advertiser;
	}

	public HttpResponse handle(HttpRequest httpRequest) throws Exception {
		PairSetupRequest req = PairSetupRequest.of(httpRequest.getBody());
		
		if (req.getStage() == Stage.ONE)  {
			logger.info("Starting pair for "+registry.getLabel());
			srpHandler = new SrpHandler(authInfo.getPin(), authInfo.getSalt());
			return srpHandler.handle(req);
		} else if (req.getStage() == Stage.TWO) {
			logger.debug("Entering second stage of pair for "+registry.getLabel());
			if (srpHandler == null) {
				logger.warn("Received unexpected stage 2 request for "+registry.getLabel());
				return new UnauthorizedResponse();
			} else {
				try {
					return srpHandler.handle(req);
				} catch (Exception e) {
					srpHandler = null; //You don't get to try again - need a new key
					logger.error("Exception encountered while processing pairing request", e);
					return new UnauthorizedResponse();
				}
			}
		} else if (req.getStage() == Stage.THREE) {
			logger.debug("Entering third stage of pair for "+registry.getLabel());
			if (srpHandler == null) {
				logger.warn("Received unexpected stage 3 request for "+registry.getLabel());
				return new UnauthorizedResponse();
			} else {
				FinalPairHandler handler = new FinalPairHandler(srpHandler.getK(), authInfo, advertiser);
				try {
					return handler.handle(req);
				} catch (Exception e) {
					logger.error("Exception while finalizing pairing", e);
					return new UnauthorizedResponse();
				}
			}
		} 
		
		return new NotFoundResponse();
	}
}
