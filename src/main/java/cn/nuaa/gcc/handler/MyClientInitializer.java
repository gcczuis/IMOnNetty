package cn.nuaa.gcc.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * {@author: gcc}
 * {@Date: 2019/3/16 10:54}
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
//        pipeline.addLast(new MyByteToLongDecoder());
        pipeline.addLast(new MyByteToLongDecoder2());
        pipeline.addLast(new MyLongToByteEncoder());
        pipeline.addLast(new MyClientHandler());

    }
}
