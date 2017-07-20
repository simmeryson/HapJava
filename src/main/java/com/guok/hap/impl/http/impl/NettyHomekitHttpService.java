package com.guok.hap.impl.http.impl;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;

import com.guok.hap.impl.http.HomekitClientConnectionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;

class NettyHomekitHttpService {

    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;

    private final static Logger logger = LoggerFactory.getLogger(NettyHomekitHttpService.class);
    private final ChannelGroup allChannels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private final int port;
    private final int nThreads;

    public static NettyHomekitHttpService create(int port, int nThreads) {
        return new NettyHomekitHttpService(port, nThreads);
    }

    private NettyHomekitHttpService(int port, int nThreads) {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        this.port = port;
        this.nThreads = nThreads;
    }

    public ListenableFuture<Integer> start(HomekitClientConnectionFactory connectionFactory) {
        final SettableFuture<Integer> portFuture = SettableFuture.create();
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ServerInitializer(connectionFactory, allChannels, nThreads))
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        final ChannelFuture bindFuture = b.bind(port);
        bindFuture.addListener(new GenericFutureListener<Future<? super Void>>() {

            @Override
            public void operationComplete(Future<? super Void> future)
                    throws Exception {
                try {
                    future.get();
                    SocketAddress socketAddress = bindFuture.channel().localAddress();
                    if (socketAddress instanceof InetSocketAddress) {
                        logger.info("Bound homekit listener to " + socketAddress.toString());
                        portFuture.set(((InetSocketAddress) socketAddress).getPort());
                    } else {
                        throw new RuntimeException("Unknown socket address type: " + socketAddress.getClass().getName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    portFuture.setException(e);
                }
            }
        });
        return portFuture;
    }

    public void shutdown() {
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }

    public void resetConnections() {
        logger.info("Resetting connections");
        allChannels.close();
    }
}
