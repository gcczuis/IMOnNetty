package cn.nuaa.gcc.fourthExample;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;


/**
 * {@author: gcc}
 * {@Date: 2019/3/16 22:41}
 */
public class MyInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //读空闲3s，写空闲4s，读写空闲5s
        pipeline.addLast(new IdleStateHandler(3,4,2, TimeUnit.SECONDS));
        //加入检测事件处理器
        pipeline.addLast(new MyServerHandler());
    }
}
