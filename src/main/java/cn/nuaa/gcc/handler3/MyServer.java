package cn.nuaa.gcc.handler3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * {@author: gcc}
 * {@Date: 2019/3/16 10:37}
 * 通过自定义协议来解决tcp粘包的情况
 *
 * 这里我在解码阶段{@link MyDecoderProtocol}通过继承{@link io.netty.handler.codec.ReplayingDecoder}简化了解码操作，
 * 但为了充分理解其checkpoint特性，我又增加了checkpoint，增加了不必要的复杂度，复习时务必充分理解{@link MyDecoderProtocol}的编写手法
 *
 */
public class MyServer {
    public static void main(String[] args){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).
                    childHandler(new MyServerInitializer());
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
