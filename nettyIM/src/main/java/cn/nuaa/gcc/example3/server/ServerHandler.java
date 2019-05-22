package cn.nuaa.gcc.example3.server;

import cn.nuaa.gcc.example3.attribute.Attributes;
import cn.nuaa.gcc.example3.protocol.command.*;
import cn.nuaa.gcc.example3.util.LoginUtil;
import com.sun.net.httpserver.Authenticator;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * {@author: gcc}
 * {@Date: 2019/4/13 17:40}
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        //解码
        Packet packet = PacketCodeC.INSTANCE.decode(buf);

        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(packet.getVersion());
        //判断是否是登录请求包
        if (packet instanceof LoginRequestPacket) {
            System.out.println("接收到客户端登录请求");
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            //登录校验
            if (valid(loginRequestPacket)) {
                //校验成功
                loginResponsePacket.setSuccess(true);
                System.out.println("客户端登录成功");
            } else {
                //校验失败
                loginResponsePacket.setSuccess(false);
                loginResponsePacket.setReason("账号密码校验失败");
            }
            ByteBuf buffer = PacketCodeC.INSTANCE.encode(loginResponsePacket);
            ctx.channel().writeAndFlush(buffer);
        } else if (packet instanceof MessageRequestPacket) {
            //发送消息的时候客户端已经做了登录验证，所以服务端就不用进行登录验证啦
            MessageRequestPacket requestPacket = (MessageRequestPacket) packet;
            System.out.println(new Date() + " 收到客户端消息：" + requestPacket.getMessage());

            MessageResponsePacket responsePacket = new MessageResponsePacket();
            responsePacket.setVersion(requestPacket.getVersion());
            responsePacket.setResponse("【服务端】回复：" + requestPacket.getMessage());
            ByteBuf response = PacketCodeC.INSTANCE.encode(responsePacket);
            ctx.channel().writeAndFlush(response);

        }

    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
