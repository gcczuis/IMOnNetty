package cn.nuaa.gcc.example4.handler;

import cn.nuaa.gcc.example4.protocol.command.Packet;
import cn.nuaa.gcc.example4.protocol.command.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * {@author: gcc}
 * {@Date: 2019/4/13 22:01}
 */
public class BytesToPacketDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Packet packet = PacketCodeC.INSTANCE.decode(in);
        out.add(packet);
    }
}
