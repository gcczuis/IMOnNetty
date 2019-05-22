package cn.nuaa.gcc.im.client.handler;

import cn.nuaa.gcc.im.protocol.response.CreateGroupResponsePacket;
import cn.nuaa.gcc.im.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


/**
 * {@author: gcc}
 * {@Date: 2019/4/16 00:08}
 */
@ChannelHandler.Sharable
public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {
    public static final CreateGroupResponseHandler INSTANCE = new CreateGroupResponseHandler();

    private CreateGroupResponseHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket msg) throws Exception {
        System.out.print("群创建成功，id 为[" + msg.getGroupId() + "], ");
        System.out.println("群里面有：" + msg.getUserNameList());

        SessionUtil.addChannelGroup(msg.getGroupId(),ctx.channel());
    }
}
