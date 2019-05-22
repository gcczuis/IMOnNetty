package cn.nuaa.gcc.im.client.handler;

import cn.nuaa.gcc.im.protocol.response.SendToGroupResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * {@author: gcc}
 * {@Date: 2019/4/16 10:12}
 */
@ChannelHandler.Sharable
public class SendToGroupResponseHandler extends SimpleChannelInboundHandler<SendToGroupResponsePacket> {
    public static final SendToGroupResponseHandler INSTANCE = new SendToGroupResponseHandler();

    private SendToGroupResponseHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendToGroupResponsePacket msg) throws Exception {
        if(msg.isSuccess()){
            System.out.println(msg.getMessage());
        }else{
            System.out.println("发送群聊天内容失败，原因："+msg.getReason());
        }
    }
}
