package cn.nuaa.gcc.handler3;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * {@author: gcc}
 * {@Date: 2019/3/16 10:55}
 */
public class MyClientHandler extends SimpleChannelInboundHandler<MyProtocol> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyProtocol msg) throws Exception {

        int length = msg.getLength();
        byte[] content = msg.getContent();

        System.out.println("服务端接收到的数据：");
        System.out.println("长度："+length);
        System.out.println("内容："+new String(content, Charset.forName("utf-8")));

        System.out.println("服务端接收到的消息数量："+(++count));
        System.out.println();


    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            String message = "send from client";
            byte[] content = message.getBytes(Charset.forName("utf-8"));
            MyProtocol protocol = new MyProtocol();
            protocol.setContent(content);
            protocol.setLength(content.length);
            ctx.writeAndFlush(protocol);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
