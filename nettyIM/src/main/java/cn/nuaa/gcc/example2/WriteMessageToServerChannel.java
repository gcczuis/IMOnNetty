package cn.nuaa.gcc.example2;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

/**
 * {@author: gcc}
 * {@Date: 2019/4/11 22:39}
 */
public class WriteMessageToServerChannel extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client WriteMessageToServerChannel active");
        ByteBuf byteBuf = getByteBuf(ctx);
        ctx.writeAndFlush(byteBuf);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        ByteBuf buffer = ctx.alloc().buffer();
        byte[] buf = "我是客户端，你是服务端嘛".getBytes(Charset.forName("utf-8"));
        buffer.writeBytes(buf);
        return buffer;

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        System.out.println("接收到服务端的消息："+new String(bytes, Charset.forName("utf-8")));
    }
}
