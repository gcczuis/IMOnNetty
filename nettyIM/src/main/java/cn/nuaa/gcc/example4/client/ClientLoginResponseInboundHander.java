package cn.nuaa.gcc.example4.client;

import cn.nuaa.gcc.example4.protocol.command.LoginRequestPacket;
import cn.nuaa.gcc.example4.protocol.command.LoginResponsePacket;
import cn.nuaa.gcc.example4.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;

/**
 * {@author: gcc}
 * {@Date: 2019/4/14 09:31}
 */
public class ClientLoginResponseInboundHander extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        if (msg.isSuccess()) {
            System.out.println(new Date() + ":客户端登录成功");
            LoginUtil.markAsLogin(ctx.channel());
            new Thread(new ReadConsoleToServerTask(ctx.channel())).start();
        } else {
            System.out.println(new Date() + ":客户端登录失败，原因" + msg.getReason());
        }

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + "客户端开始登陆");

        //创建登录对象
        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUserId(UUID.randomUUID().toString());
        packet.setUsername("gcc");
        packet.setPassword("root");
        ctx.channel().writeAndFlush(packet);
    }
}
