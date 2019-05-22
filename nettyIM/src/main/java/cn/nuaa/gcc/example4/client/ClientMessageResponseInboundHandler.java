package cn.nuaa.gcc.example4.client;

import cn.nuaa.gcc.example4.protocol.command.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * {@author: gcc}
 * {@Date: 2019/4/14 09:33}
 */
public class ClientMessageResponseInboundHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket msg) throws Exception {
        String response = msg.getResponse();
        System.out.println("服务端回复："+response);
    }
}
