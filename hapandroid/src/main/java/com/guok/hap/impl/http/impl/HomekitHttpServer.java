package com.guok.hap.impl.http.impl;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.impl.HomekitWebHandler;
import com.guok.hap.impl.http.HomekitClientConnectionFactory;

public class HomekitHttpServer implements HomekitWebHandler {

	private volatile NettyHomekitHttpService service = null;
	private final int port;
	private final int nThreads;

	@Override
	public void stop() {
		if (this.service != null) {
			this.service.shutdown();
			this.service = null;
		}
	}
	
	public HomekitHttpServer(int port, int nThreads) {
		this.port = port;
		this.nThreads = nThreads;
	}

	@Override
	public ListenableFuture<Integer> start(HomekitClientConnectionFactory clientConnectionFactory) {
		if (service == null) {
			this.service = NettyHomekitHttpService.create(port, nThreads);
			return this.service.start(clientConnectionFactory);
		} else {
			throw new RuntimeException("HomekitHttpServer can only be started once");
		}
	}

	@Override
	public void resetConnections() {
		service.resetConnections();
	}

}
