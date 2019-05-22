package cn.nuaa.gcc.im.client.handler;

import cn.nuaa.gcc.im.protocol.response.LogOutResponsePacket;
import cn.nuaa.gcc.im.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * {@author: gcc}
 * {@Date: 2019/4/16 00:16}
 */
@ChannelHandler.Sharable
public class LogOutResponseHandler extends SimpleChannelInboundHandler<LogOutResponsePacket> {
    public static final LogOutResponseHandler INSTANCE = new LogOutResponseHandler();

    private LogOutResponseHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogOutResponsePacket msg) throws Exception {
        if (msg.isSuccess()) {
            String userName = SessionUtil.getSession(ctx.channel()).getUserName();
            SessionUtil.unBindSession(ctx.channel());
            System.out.println(userName + "退出登录成功");
        }
    }
}
