package cn.nuaa.gcc.example4.server;

import cn.nuaa.gcc.example4.protocol.command.LoginRequestPacket;
import cn.nuaa.gcc.example4.protocol.command.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * {@author: gcc}
 * {@Date: 2019/4/14 09:18}
 */
class ServerLoginInboundHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        System.out.println("接收到客户端登录请求");
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(msg.getVersion());
        //登录校验
        if (valid(msg)) {
            //校验成功
            loginResponsePacket.setSuccess(true);
            System.out.println("客户端登录成功");
        } else {
            //校验失败
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("账号密码校验失败");
        }
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }

}
