package cn.nuaa.gcc.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * {@author: gcc}
 * {@Date: 2019/3/31 17:51}
 */
public class MyLongToByteEncoder extends MessageToByteEncoder<Long> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("MyEncoder invoked");
        System.out.println(msg);
        out.writeLong(msg);
    }
}
