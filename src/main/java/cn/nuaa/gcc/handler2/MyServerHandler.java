package cn.nuaa.gcc.handler2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;


/**
 * {@author: gcc}
 * {@Date: 2019/3/16 10:42}
 */
public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {
        byte[] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);
        String message = new String(bytes, Charset.forName("utf-8"));
        System.out.println("服务端接收到的消息内容："+message);
        System.out.println("服务端接收到的消息数量："+(++this.count));

        ByteBuf byteBuf = ctx.alloc().directBuffer();
        byteBuf.writeCharSequence(UUID.randomUUID().toString(),Charset.forName("utf-8"));
        ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
