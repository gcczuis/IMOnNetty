package cn.nuaa.gcc.im.client.handler;

import cn.nuaa.gcc.im.protocol.response.QuitGroupResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * {@author: gcc}
 * {@Date: 2019/4/16 15:46}
 */
@ChannelHandler.Sharable
public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {
    public static final QuitGroupResponseHandler INSTANCE = new QuitGroupResponseHandler();

    private QuitGroupResponseHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupResponsePacket msg) throws Exception {
        if(!msg.isSuccess()){
            System.out.println("退出群聊【"+msg.getGroupId()+"】失败，原因:"+msg.getReason());
        }else{
            System.out.println("退出群聊【"+msg.getGroupId()+"】成功");
        }
    }
}
