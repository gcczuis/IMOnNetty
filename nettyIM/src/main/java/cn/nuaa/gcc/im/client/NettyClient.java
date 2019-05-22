package cn.nuaa.gcc.im.client;

import cn.nuaa.gcc.im.client.Console.ConsoleCommandManager;
import cn.nuaa.gcc.im.client.handler.*;
import cn.nuaa.gcc.im.codec.PacketCodecHandler;
import cn.nuaa.gcc.im.handler.IMIdleStateHandler;
import cn.nuaa.gcc.im.protocol.request.LoginRequestPacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import cn.nuaa.gcc.im.codec.Spliter;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author 闪电侠
 */
public class NettyClient {
    private static final int MAX_RETRY = 5;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8000;


    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        //这个处理器得放在前面，如果放在后面的话，一旦消息被前面的处理器消费了
                        //那么该这个处理心跳的处理器以为没收到消息，就会关闭连接
                        ch.pipeline().addLast(new IMIdleStateHandler());
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
                        // 登录响应处理器
                        ch.pipeline().addLast(LoginResponseHandler.INSTANCE);
                        // 收消息处理器
                        ch.pipeline().addLast(MessageResponseHandler.INSTANCE);
                        // 登出响应处理器
                        ch.pipeline().addLast(LogOutResponseHandler.INSTANCE);
                        // 创建群响应处理器
                        ch.pipeline().addLast(CreateGroupResponseHandler.INSTANCE);
                        // 群消息响应
                        ch.pipeline().addLast(SendToGroupResponseHandler.INSTANCE);
                        // 获取群成员响应处理器
                        ch.pipeline().addLast(ListGroupMembersResponseHandler.INSTANCE);
                        // 加群响应处理器
                        ch.pipeline().addLast(JoinGroupResponseHandler.INSTANCE);
                        // 退群响应处理器
                        ch.pipeline().addLast(QuitGroupResponseHandler.INSTANCE);
                        //心跳定时器
                        ch.pipeline().addLast(new BeatHeartTimerHander());
                    }
                });

        connect(bootstrap, HOST, PORT, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + ": 连接成功，启动控制台线程……");
                Channel channel = ((ChannelFuture) future).channel();
                startConsoleThread(channel);
            } else if (retry == 0) {
                System.err.println("重试次数已用完，放弃连接！");
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }

    private static void startConsoleThread(Channel channel) {

        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();Scanner sc = new Scanner(System.in);

        new Thread(() -> {
            while (!Thread.interrupted()) {
                consoleCommandManager.exec(sc, channel);

            }
        }).start();
    }


    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
