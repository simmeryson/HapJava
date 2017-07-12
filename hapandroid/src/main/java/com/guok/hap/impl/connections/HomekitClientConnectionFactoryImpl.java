package com.guok.hap.impl.connections;


import com.guok.hap.BridgeAuthInfo;
import com.guok.hap.impl.Consumer;
import com.guok.hap.impl.HomekitRegistry;
import com.guok.hap.impl.advertiser.IAdvertiser;
import com.guok.hap.impl.http.HomekitClientConnection;
import com.guok.hap.impl.http.HomekitClientConnectionFactory;
import com.guok.hap.impl.http.HttpResponse;

public class HomekitClientConnectionFactoryImpl implements HomekitClientConnectionFactory{

	private final BridgeAuthInfo authInfo;
	private final HomekitRegistry registry;
	private final SubscriptionManager subscriptions;
	private final IAdvertiser advertiser;
	
	public HomekitClientConnectionFactoryImpl(BridgeAuthInfo authInfo,
			HomekitRegistry registry, SubscriptionManager subscriptions, IAdvertiser advertiser) {
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
