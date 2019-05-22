package cn.nuaa.gcc.im.server.handler;

import cn.nuaa.gcc.im.protocol.request.BeatHeartRequestPacket;
import cn.nuaa.gcc.im.protocol.response.BeatHeartResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * {@author: gcc}
 * {@Date: 2019/4/17 16:40}
 * 该类收到客户端发来的心跳时，向客户端回发一个心跳包，让客户端也进行心跳检测
 */
@ChannelHandler.Sharable
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<BeatHeartRequestPacket> {
    public static final HeartBeatRequestHandler INSTANCE = new HeartBeatRequestHandler();

    private HeartBeatRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BeatHeartRequestPacket msg) throws Exception {
        BeatHeartResponsePacket beatHeartResponsePacket = new BeatHeartResponsePacket();
        ctx.channel().writeAndFlush(beatHeartResponsePacket);
    }
}
