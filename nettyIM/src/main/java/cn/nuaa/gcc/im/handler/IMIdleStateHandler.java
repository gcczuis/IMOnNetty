package cn.nuaa.gcc.im.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * {@author: gcc}
 * {@Date: 2019/4/17 16:31}
 */
public class IMIdleStateHandler extends IdleStateHandler {
    private static final int READER_IDLE_TIME = 15;

    public IMIdleStateHandler() {
        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }


    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        if (evt.state() == IdleState.READER_IDLE) {
            System.out.println(READER_IDLE_TIME + "秒内未读到数据，关闭连接");
            ctx.channel().close();
            return;
        }
        System.out.println("发生了其他事件:" + evt.state());

    }
}
