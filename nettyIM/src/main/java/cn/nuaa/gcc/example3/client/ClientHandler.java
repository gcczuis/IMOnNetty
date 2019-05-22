package cn.nuaa.gcc.example3.client;

import cn.nuaa.gcc.example4.protocol.command.LoginRequestPacket;
import cn.nuaa.gcc.example4.protocol.command.LoginResponsePacket;
import cn.nuaa.gcc.example4.protocol.command.Packet;
import cn.nuaa.gcc.example4.protocol.command.PacketCodeC;
import cn.nuaa.gcc.example4.util.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;
import java.util.UUID;

/**
 * {@author: gcc}
 * {@Date: 2019/4/13 17:19}
 */
class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + "客户端开始登陆");

        //创建登录对象
        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUserId(UUID.randomUUID().toString());
        packet.setUsername("gcc");
        packet.setPassword("root");

        ByteBuf buf = PacketCodeC.INSTANCE.encode(packet,ctx.alloc().buffer());
        ctx.channel().writeAndFlush(buf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        Packet packet = PacketCodeC.INSTANCE.decode(buf);
        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket realPacket = (LoginResponsePacket) packet;
            if (realPacket.isSuccess()) {
                System.out.println(new Date() + ":客户端登录成功");
                LoginUtil.markAsLogin(ctx.channel());
                new Thread(new ReadConsoleToServerTask(ctx.channel())).start();
            } else {
                System.out.println(new Date() + ":客户端登录失败，原因" + realPacket.getReason());
            }
        }
    }


}
