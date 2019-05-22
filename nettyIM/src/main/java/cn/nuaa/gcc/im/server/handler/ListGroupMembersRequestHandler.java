package cn.nuaa.gcc.im.server.handler;

import cn.nuaa.gcc.im.protocol.request.ListGroupMembersRequestPacket;
import cn.nuaa.gcc.im.protocol.response.ListGroupMembersResponsePacket;
import cn.nuaa.gcc.im.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.ArrayList;

/**
 * {@author: gcc}
 * {@Date: 2019/4/16 11:00}
 */
@ChannelHandler.Sharable
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {
    public static final ListGroupMembersRequestHandler INSTANCE = new ListGroupMembersRequestHandler();

    private ListGroupMembersRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket msg) throws Exception {
        String groupId = msg.getGroupId();
        ListGroupMembersResponsePacket groupMembersResponsePacket = new ListGroupMembersResponsePacket();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);

        if (channelGroup == null) {
            groupMembersResponsePacket.setSuccess(false);
            groupMembersResponsePacket.setReason("服务器不存在该groupId");
        }else{
            ArrayList<String> list = new ArrayList<>();
            for (Channel channel : channelGroup) {
                list.add(SessionUtil.getSession(channel).getUserName());
            }

            groupMembersResponsePacket.setSuccess(true);
            groupMembersResponsePacket.setMemberList(list);
        }
        groupMembersResponsePacket.setGroupId(groupId);

        ctx.channel().writeAndFlush(groupMembersResponsePacket);
    }
}
