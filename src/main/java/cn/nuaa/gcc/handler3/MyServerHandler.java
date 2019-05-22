package cn.nuaa.gcc.handler3;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;


/**
 * {@author: gcc}
 * {@Date: 2019/3/16 10:42}
 */
public class MyServerHandler extends SimpleChannelInboundHandler<MyProtocol> {
    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyProtocol msg) {
        int length = msg.getLength();
        byte[] content = msg.getContent();

        System.out.println("服务端接收到的数据：");
        System.out.println("长度："+length);
        System.out.println("内容："+new String(content, Charset.forName("utf-8")));

        System.out.println("服务端接收到的消息数量："+(++count));

        byte[] uuid = UUID.randomUUID().toString().getBytes(Charset.forName("utf-8"));
        MyProtocol message = new MyProtocol();
        message.setLength(uuid.length);
        message.setContent(uuid);

        ctx.channel().writeAndFlush(message);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
