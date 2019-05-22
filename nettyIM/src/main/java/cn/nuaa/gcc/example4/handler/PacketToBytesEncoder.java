package cn.nuaa.gcc.example4.handler;

import cn.nuaa.gcc.example4.protocol.command.Packet;
import cn.nuaa.gcc.example4.protocol.command.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * {@author: gcc}
 * {@Date: 2019/4/13 21:56}
 */
public class PacketToBytesEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        PacketCodeC.INSTANCE.encode(msg, out);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    }
}

