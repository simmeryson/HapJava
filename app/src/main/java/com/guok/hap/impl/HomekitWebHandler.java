package com.guok.hap.impl;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.impl.http.HomekitClientConnectionFactory;


public interface HomekitWebHandler {

	ListenableFuture<Integer> start(HomekitClientConnectionFactory clientConnectionFactory);
	
	void stop();

	void resetConnections();
	
}
