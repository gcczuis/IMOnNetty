package cn.nuaa.gcc.im.client.handler;

import cn.nuaa.gcc.im.protocol.response.JoinGroupResponsePacket;
import cn.nuaa.gcc.im.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * {@author: gcc}
 * {@Date: 2019/4/16 15:19}
 */
@ChannelHandler.Sharable
public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {
    public static final JoinGroupResponseHandler INSTANCE = new JoinGroupResponseHandler();

    private JoinGroupResponseHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponsePacket msg) throws Exception {
        System.out.println("=-=-==");
        if(!msg.isSuccess()){
            System.err.println("加入群[" + msg.getGroupId() + "]失败，原因为：" + msg.getReason());
        }else{
            System.out.println("加入群[" + msg.getGroupId() + "]成功");
            SessionUtil.addChannelGroup(msg.getGroupId(),ctx.channel());
        }
    }
}
