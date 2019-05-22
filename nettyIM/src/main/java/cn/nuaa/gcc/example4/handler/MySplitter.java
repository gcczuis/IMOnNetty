package cn.nuaa.gcc.example4.handler;

import cn.nuaa.gcc.example4.protocol.command.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * {@author: gcc}
 * {@Date: 2019/4/13 22:05}
 */
public class MySplitter extends LengthFieldBasedFrameDecoder {
    private static final int LENGTH_FIELD_OFFSET = 7;
    private static final int LENGTH_FIELD_LENGTH = 4;

    public MySplitter() {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH,0,0);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (in.readableBytes() >= 4 && in.getInt(in.readerIndex()) == PacketCodeC.MAGIC_NUMBER) {
            return super.decode(ctx, in);
        }
        ctx.channel().close();
        return null;
    }
}
