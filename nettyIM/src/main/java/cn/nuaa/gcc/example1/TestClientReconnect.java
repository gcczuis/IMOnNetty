package cn.nuaa.gcc.example1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * {@author: gcc}
 * {@Date: 2019/4/11 16:37}
 * 通常情况下，连接建立失败不会立即重新连接，而是会通过一个指数退避的方式，比如每隔 1 秒、2 秒、4 秒、8 秒，
 * 以 2 的幂次来建立连接，然后到达一定次数之后就放弃连接，接下来我们就来实现一下这段逻辑，我们默认重试 5 次
 */
public class TestClientReconnect {
    private static final int MAX_RETRY = 5;

    public static void main(String[] args) throws UnknownHostException {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup).
                //表示是否开启 TCP 底层心跳机制，true 为开启
                        option(ChannelOption.SO_KEEPALIVE, true).
                //表示是否开始 Nagle 算法，true 表示关闭，false 表示开启，通俗地说，如果要求高实时性，有数据发送时就马上发送，就设置为 true 关闭，如果需要减少发送次数减少网络交互，就设置为 false 开启
                        option(ChannelOption.TCP_NODELAY, true).
                //表示连接的超时时间，超过这个时间还是建立不上的话则代表连接失败
                        option(ChannelOption.SO_TIMEOUT, 5000).
                channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) {

            }
        });

        connect(bootstrap, Inet4Address.getLocalHost(), 3307, MAX_RETRY);
    }

    private static void connect(final Bootstrap bootstrap, final InetAddress localHost, final int port, final int retry) {
        bootstrap.connect(localHost, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功!");
            } else if (retry == 0) {
                System.out.println("重试次数已用完，放弃连接！");
                bootstrap.config().group().shutdownGracefully();
            } else {
                //重试次数
                int order = MAX_RETRY - retry + 1;
                int waitTime = 1 << order;
                System.out.println(waitTime);
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(() -> {
                    connect(bootstrap, localHost, port, retry - 1);
                }, waitTime, TimeUnit.SECONDS);

            }
        });
    }
}
