package cn.nuaa.gcc.im.server.handler;

import cn.nuaa.gcc.im.protocol.request.LogOutRequestPacket;
import cn.nuaa.gcc.im.protocol.response.LogOutResponsePacket;
import cn.nuaa.gcc.im.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * {@author: gcc}
 * {@Date: 2019/4/15 22:03}
 */
@ChannelHandler.Sharable
public class LogOutRequestHandler extends SimpleChannelInboundHandler<LogOutRequestPacket> {
    public static final LogOutRequestHandler INSTANCE = new LogOutRequestHandler();

    private LogOutRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogOutRequestPacket msg) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
        LogOutResponsePacket responsePacket = new LogOutResponsePacket();
        responsePacket.setSuccess(true);
        ctx.channel().writeAndFlush(responsePacket);
    }
}
