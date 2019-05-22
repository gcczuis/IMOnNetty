package cn.nuaa.gcc.handler3;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * {@author: gcc}
 * {@Date: 2019/4/1 10:36}
 */
public class MyEncoderProtocol extends MessageToByteEncoder<MyProtocol> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MyProtocol msg, ByteBuf out) throws Exception {
        System.out.println("MyEncoderProtocol invoked");
        out.writeInt(msg.getLength());
        out.writeBytes(msg.getContent());
    }
}
