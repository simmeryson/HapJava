package com.guok.hap.impl.http.impl;

import com.google.common.util.concurrent.ListenableFuture;
import com.guok.hap.impl.HomekitWebHandler;
import com.guok.hap.impl.http.HomekitClientConnectionFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class HomeKitHttpServer implements HomekitWebHandler {

    private volatile NettyHomekitHttpService service = null;
    private int port;
    private final int nThreads;
    private final InetAddress mInetAddress;

    @Override
    public void stop() {
        if (this.service != null) {
            this.service.shutdown();
            this.service = null;
        }
    }

    public HomeKitHttpServer(int port) throws UnknownHostException {
        this(InetAddress.getLocalHost(), port, Runtime.getRuntime().availableProcessors());
    }

    public HomeKitHttpServer(InetAddress inetAddress, int port) {
        this(inetAddress, port, Runtime.getRuntime().availableProcessors());
    }

    public HomeKitHttpServer(InetAddress inetAddress, int port, int nThreads) {
        this.port = port;
        this.nThreads = nThreads;
        this.mInetAddress = inetAddress;
    }

    @Override
    public ListenableFuture<Integer> start(HomekitClientConnectionFactory clientConnectionFactory) {
        if (service == null) {
            this.service = NettyHomekitHttpService.create(port, nThreads);
            return this.service.start(clientConnectionFactory);
        } else {
            throw new RuntimeException("HomeKitHttpServer can only be started once");
        }
    }

    @Override
    public void resetConnections() {
        service.resetConnections();
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public InetAddress getInetAddress() {
        return mInetAddress;
    }
}
