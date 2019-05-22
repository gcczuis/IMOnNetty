package cn.nuaa.gcc.example3.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

/**
 * {@author: gcc}
 * {@Date: 2019/4/11 16:13}
 * 由于端口有可能被占用，所以在这个例子中通过future的回调方法重新绑定端口
 * <p>
 * 做法：将bind方法抽取出来，通过bind方法增加监听方法
 */
public class Server {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).
                    //指定一些自定义属性，然后我们可以通过channel.attr()取出这个属性，AttributeKey维护了一个缓存常量
                    // 池,如果缓存常量池中已经有了该key，则会抛出异常。
                            attr(AttributeKey.newInstance("serverName"), "nettyServer").
                    channel(NioServerSocketChannel.class).
                    //追求高实时性，关闭Nagle算法
                            childOption(ChannelOption.TCP_NODELAY, true).
                    //开启tcp心跳检测机制
                            childOption(ChannelOption.SO_KEEPALIVE, true).
                    childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ServerHandler());
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync().addListener(future -> {
                if(future.isSuccess()){
                    System.out.println("端口【8899】绑定成功");
                }else{
                    System.out.println("端口【8899】绑定失败");
                }
            });
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
