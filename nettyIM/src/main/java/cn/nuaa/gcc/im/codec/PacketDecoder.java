package cn.nuaa.gcc.im.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import cn.nuaa.gcc.im.protocol.PacketCodeC;

import java.util.List;

/**
 * 与{@link PacketEncoder}合并成{@link PacketCodecHandler}
 * @deprecated
 *
 */
public class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out) {
        out.add(PacketCodeC.INSTANCE.decode(in));
    }
}
