package cn.nuaa.gcc.im.server.handler;

import cn.nuaa.gcc.im.protocol.request.SendToGroupRequestPacket;
import cn.nuaa.gcc.im.protocol.response.SendToGroupResponsePacket;
import cn.nuaa.gcc.im.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * {@author: gcc}
 * {@Date: 2019/4/16 09:56}
 */
@ChannelHandler.Sharable
public class SendToGroupRequestHandler extends SimpleChannelInboundHandler<SendToGroupRequestPacket> {
    public static final SendToGroupRequestHandler INSTANCE = new SendToGroupRequestHandler();

    private SendToGroupRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendToGroupRequestPacket msg) throws Exception {
        System.out.println(msg);
        String groupId = msg.getGroupId();
        String message = msg.getMessage();

        SendToGroupResponsePacket toGroupResponsePacket = new SendToGroupResponsePacket();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        if (channelGroup == null) {
            toGroupResponsePacket.setSuccess(false);
            toGroupResponsePacket.setReason("【" + groupId + "】不存在，发送群聊消息失败");
            System.out.println(toGroupResponsePacket);
            ctx.channel().writeAndFlush(toGroupResponsePacket);
        }else{
            toGroupResponsePacket.setSuccess(true);
            toGroupResponsePacket.setMessage("收到群【"+groupId+"】中【"+SessionUtil.getSession(ctx.channel()).getUserName()+"】发来的消息:"+message);

            channelGroup.writeAndFlush(toGroupResponsePacket);
        }
    }
}
