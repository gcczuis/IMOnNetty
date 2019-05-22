package cn.nuaa.gcc.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * {@author: gcc}
 * {@Date: 2019/4/1 09:16}
 */
public class MyLongToStringDecoder extends MessageToMessageDecoder<Long> {
    @Override
    protected void decode(ChannelHandlerContext ctx, Long msg, List<Object> out) throws Exception {
        System.out.println("MyLongToStringDecoder invoked");
        out.add(msg+" String");
    }
}
