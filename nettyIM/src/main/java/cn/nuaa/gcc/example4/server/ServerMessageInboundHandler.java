package cn.nuaa.gcc.example4.server;

import cn.nuaa.gcc.example4.protocol.command.MessageRequestPacket;
import cn.nuaa.gcc.example4.protocol.command.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * {@author: gcc}
 * {@Date: 2019/4/14 09:21}
 */
class ServerMessageInboundHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        System.out.println(new Date() + " 收到客户端消息：" + msg.getMessage());

        MessageResponsePacket responsePacket = new MessageResponsePacket();
        responsePacket.setVersion(msg.getVersion());
        responsePacket.setResponse("【服务端】回复：" + msg.getMessage());
        ctx.channel().writeAndFlush(responsePacket);
    }
}
