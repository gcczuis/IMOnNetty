package cn.nuaa.gcc.im.server.handler;

import cn.nuaa.gcc.im.protocol.request.QuitGroupRequestPacket;
import cn.nuaa.gcc.im.protocol.response.QuitGroupResponsePacket;
import cn.nuaa.gcc.im.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * {@author: gcc}
 * {@Date: 2019/4/16 15:37}
 */
@ChannelHandler.Sharable
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {
    public static final QuitGroupRequestHandler INSTANCE = new QuitGroupRequestHandler();

    private QuitGroupRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket msg) throws Exception {
        QuitGroupResponsePacket quitGroupResponsePacket = new QuitGroupResponsePacket();
        String groupId = msg.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);

        quitGroupResponsePacket.setGroupId(groupId);
        if(channelGroup!=null){
            if(channelGroup.contains(ctx.channel())){
                channelGroup.remove(ctx.channel());
                quitGroupResponsePacket.setSuccess(true);
            }else{
                quitGroupResponsePacket.setSuccess(false);
                quitGroupResponsePacket.setReason("您未加入【"+groupId+"】该群聊");
            }
        }else{
            quitGroupResponsePacket.setSuccess(false);
            quitGroupResponsePacket.setReason("群聊已被解散");
        }

        ctx.channel().writeAndFlush(quitGroupResponsePacket);
    }
}
