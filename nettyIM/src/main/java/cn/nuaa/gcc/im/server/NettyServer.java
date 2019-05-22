package cn.nuaa.gcc.im.server;

import cn.nuaa.gcc.im.codec.PacketCodecHandler;
import cn.nuaa.gcc.im.handler.IMIdleStateHandler;
import cn.nuaa.gcc.im.server.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import cn.nuaa.gcc.im.codec.Spliter;

import java.util.Date;

public class NettyServer {

    private static final int PORT = 8000;

    public static void main(String[] args) {
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        final ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(boosGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        //这个处理器得放在前面，如果放在后面的话，一旦消息被前面的处理器消费了
                        //那么该这个处理心跳的处理器以为没收到消息，就会关闭连接
                        ch.pipeline().addLast(new IMIdleStateHandler());
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
                        //这个处理器不需要登录验证,所以放在AuthHandler前面
                        ch.pipeline().addLast(HeartBeatRequestHandler.INSTANCE);
                        ch.pipeline().addLast(LoginRequestHandler.INSTANCE);

                        ch.pipeline().addLast(new AuthHandler());
                        ch.pipeline().addLast(MessageRequestHandler.INSTANCE);
                        ch.pipeline().addLast(LogOutRequestHandler.INSTANCE);
                        ch.pipeline().addLast(CreateGroupRequestHandler.INSTANCE);
                        ch.pipeline().addLast(SendToGroupRequestHandler.INSTANCE);
                        ch.pipeline().addLast(ListGroupMembersRequestHandler.INSTANCE);
                        ch.pipeline().addLast(JoinGroupRequestHandler.INSTANCE);
                        ch.pipeline().addLast(QuitGroupRequestHandler.INSTANCE);
                    }
                });


        bind(serverBootstrap, PORT);
    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + ": 端口[" + port + "]绑定成功!");
            } else {
                System.err.println("端口[" + port + "]绑定失败!");
            }
        });
    }
}
