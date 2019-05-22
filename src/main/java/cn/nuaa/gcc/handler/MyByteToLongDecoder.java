package cn.nuaa.gcc.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * {@author: gcc}
 * {@Date: 2019/3/31 17:49}
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyDecoder invoked");
        System.out.println(in.readableBytes());
        if(in.readableBytes() >= 8){
            out.add(in.readLong());
        }
    }
}
