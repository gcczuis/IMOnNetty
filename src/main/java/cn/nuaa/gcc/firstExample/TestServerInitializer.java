package cn.nuaa.gcc.firstExample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * {@author: gcc}
 * {@Date: 2019/3/16 09:25}
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    //一旦这个channel被注册之后，这个initChannel方法就会被调用
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("httpServerCodec",new HttpServerCodec());
        pipeline.addLast("testHttpServerHandle",new TestHttpServerHandler());


    }
}
