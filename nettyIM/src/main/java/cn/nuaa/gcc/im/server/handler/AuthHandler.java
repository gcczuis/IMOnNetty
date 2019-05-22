package cn.nuaa.gcc.im.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import cn.nuaa.gcc.im.util.SessionUtil;

/**
 * 我们在 MessageRequestHandler 之前插入了一个 AuthHandler，因此 MessageRequestHandler 以及后续所有指令相关的 handler（后面小节会逐个添加）的处理都会经过 AuthHandler 的一层过滤，只要在 AuthHandler 里面处理掉身份认证相关的逻辑，后续所有的 handler 都不用操心身份认证这个逻辑
 */
public class AuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!SessionUtil.hasLogin(ctx.channel())) {
            ctx.channel().close();
        } else {
            //如果已经经过权限认证，那么就直接调用 pipeline 的 remove() 方法删除自身,删除之后，这条客户端连接的逻辑链中就不再有这段校验身份逻辑了。
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }
}
