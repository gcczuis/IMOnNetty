package cn.nuaa.gcc.example4.handler;

import cn.nuaa.gcc.example4.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * {@author: gcc}
 * {@Date: 2019/4/14 16:03}
 */
public class AuthHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(LoginUtil.hasLogin(ctx.channel())){
            ctx.channel().pipeline().remove(this);
            super.channelRead(ctx, msg);
        }else{
            ctx.channel().close();
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if (LoginUtil.hasLogin(ctx.channel())) {
            System.out.println("当前连接登录验证完毕，无需再次验证, AuthHandler 被移除");
        } else {
            System.out.println("无登录验证，强制关闭连接!");
        }
    }
}
