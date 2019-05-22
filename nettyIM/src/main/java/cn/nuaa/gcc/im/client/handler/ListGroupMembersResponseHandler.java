package cn.nuaa.gcc.im.client.handler;

import cn.nuaa.gcc.im.protocol.response.ListGroupMembersResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * {@author: gcc}
 * {@Date: 2019/4/16 11:08}
 */
public class ListGroupMembersResponseHandler extends SimpleChannelInboundHandler<ListGroupMembersResponsePacket> {
    public static final ListGroupMembersResponseHandler INSTANCE = new ListGroupMembersResponseHandler();

    private ListGroupMembersResponseHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersResponsePacket msg) throws Exception {
        if(!msg.isSuccess()){
            System.out.println("获取【"+msg.getGroupId()+"】的群成员失败,原因:"+msg.getReason());
        }else{
            System.out.println("【"+msg.getGroupId()+"】的群成员为:"+msg.getMemberList());
        }
    }
}
