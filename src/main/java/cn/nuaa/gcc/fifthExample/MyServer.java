package cn.nuaa.gcc.fifthExample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * {@author: gcc}
 * {@Date: 2019/3/16 11:08}
 * 主题：
 * 浏览器长连接
 * 场景需求：
 * 客户端A向服务端发送一个消息，服务端需要将这个消息【推送】给其他的客户端。这在目前的http1.0和http1.1协议中是不可能实现的
 * 方案1：在websocket没有提出之前，大家通常使用一种轮询的方案，客户端每隔固定间隔访问服务器端查看是否有消息。
 * 缺点：这种方案不是实时的，只能说是准实时的，而且占用了大量无意义的网络资源。
 * 方案2：websocket真正的长连接技术。
 * 优点：
 * 1.客户端和服务器端建立好websocket长连接后，就不再区分客户端和服务端，这俩端就是完全对等的，能够真正实现服务端push的功能
 * 2.客户端只有初次请求建立连接的时候会发送一些请求头信息，连接建立之后每次发送信息只有信息本身，不会像http连接这种每次请求都会携带
 *   请求头信息（某些时候请求头信息甚至超过了真正要发送信息的大小，占用了带宽）
 *
 * 注意：虽然在这台机器上如果网页关闭或者idea程序关闭，另一端都能感知到其已经失去连接。
 *      但是如果在局域网内两台电脑建立长连接，其中一台电脑关闭wifi，另一台电脑是感知不到其失去连接的
 *          或者手机是客户端，手机开启飞行模式，服务端也感知不到其失去连接。
 *
 * 解决：使用fourthExample中提到的心跳检测。
 *
 */
public class MyServer {
    public static void main(String[] args){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).
                    handler(new LoggingHandler(LogLevel.INFO)).childHandler(new MyInitializer());
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
