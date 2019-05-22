package cn.nuaa.gcc.im.server.handler;

import cn.nuaa.gcc.im.protocol.request.JoinGroupRequestPacket;
import cn.nuaa.gcc.im.protocol.response.JoinGroupResponsePacket;
import cn.nuaa.gcc.im.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * {@author: gcc}
 * {@Date: 2019/4/16 11:29}
 */
@ChannelHandler.Sharable
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {
    public static final JoinGroupRequestHandler INSTANCE = new JoinGroupRequestHandler();

    private JoinGroupRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket msg) throws Exception {
        JoinGroupResponsePacket joinGroupResponsePacket = new JoinGroupResponsePacket();

        String groupId = msg.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        if(channelGroup.contains(ctx.channel())){
            joinGroupResponsePacket.setGroupId(groupId);
            joinGroupResponsePacket.setSuccess(false);
            joinGroupResponsePacket.setReason("您已加入群【"+groupId+"】,请勿重复申请加群");
        }else{
            joinGroupResponsePacket.setGroupId(groupId);
            joinGroupResponsePacket.setSuccess(true);
            channelGroup.add(ctx.channel());
        }

        ctx.channel().writeAndFlush(joinGroupResponsePacket);
    }
}
