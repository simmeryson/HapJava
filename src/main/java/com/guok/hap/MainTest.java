package com.guok.hap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by guokai on 21/06/2017.
 */
public class MainTest {

    private static final int PORT = 9126;
    private static final String HOST = "192.168.3.56";

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        try {
            HomekitServer homekit = new HomekitServer(PORT);
            HomekitRoot bridge = homekit.createBridge(new MockAuthInfo(), "Test Bridge", "TestBridge, Inc.", "G6", "111abe234");
            bridge.addAccessory(new MockSwitch());
            bridge.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        normalNetty();
    }

    private static void normalNetty() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            // 注册handler
                            ch.pipeline().addLast(new HelloServerInHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(PORT).sync();

            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }


    static public class HelloServerInHandler extends ChannelInboundHandlerAdapter {
        private static Logger logger = LoggerFactory
                .getLogger(HelloServerInHandler.class);

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg)
                throws Exception {
            logger.info("HelloServerInHandler.channelRead");
            ByteBuf result = (ByteBuf) msg;
            byte[] result1 = new byte[result.readableBytes()];
            // msg中存储的是ByteBuf类型的数据，把数据读取到byte[]中
            result.readBytes(result1);
            String resultStr = new String(result1);
            // 接收并打印客户端的信息
            System.out.println("Client said:" + resultStr);
            // 释放资源，这行很关键
            result.release();

            // 向客户端发送消息
            String response = "I am ok!";
            // 在当前场景下，发送的数据必须转换成ByteBuf数组
            ByteBuf encoded = ctx.alloc().buffer(4 * response.length());
            encoded.writeBytes(response.getBytes());
            ctx.write(encoded);
            ctx.flush();
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }
    }

}
