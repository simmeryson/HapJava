package com.guok.hap.impl.connections;


import com.guok.hap.HomekitAuthInfo;
import com.guok.hap.impl.HomekitRegistry;
import com.guok.hap.impl.http.HomekitClientConnection;
import com.guok.hap.impl.http.HomekitClientConnectionFactory;
import com.guok.hap.impl.http.HttpResponse;
import com.guok.hap.impl.jmdns.JmdnsHomekitAdvertiser;

import com.guok.hap.impl.Consumer;

public class HomekitClientConnectionFactoryImpl implements HomekitClientConnectionFactory{

	private final HomekitAuthInfo authInfo;
	private final HomekitRegistry registry;
	private final SubscriptionManager subscriptions;
	private final JmdnsHomekitAdvertiser advertiser;
	
	public HomekitClientConnectionFactoryImpl(HomekitAuthInfo authInfo,
			HomekitRegistry registry, SubscriptionManager subscriptions, JmdnsHomekitAdvertiser advertiser) {
		this.registry = registry;
		this.authInfo = authInfo;
		this.subscriptions = subscriptions;
		this.advertiser = advertiser;
	}
	
	@Override
	public HomekitClientConnection createConnection(Consumer<HttpResponse> outOfBandMessageCallback) {
		return new ConnectionImpl(authInfo, registry, outOfBandMessageCallback, subscriptions, advertiser);
	}

	
	
}
