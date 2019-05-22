package cn.nuaa.gcc.im.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import cn.nuaa.gcc.im.protocol.Packet;
import cn.nuaa.gcc.im.protocol.PacketCodeC;

/**
 *
 * 与{@link PacketDecoder}合并成{@link PacketCodecHandler}
 * @deprecated
 *
 *
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) {
        PacketCodeC.INSTANCE.encode(out, packet);
    }
}
