package cn.nuaa.gcc.im.client.handler;

import cn.nuaa.gcc.im.protocol.request.BeatHeartRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

/**
 * {@author: gcc}
 * {@Date: 2019/4/17 16:19}
 */

public class BeatHeartTimerHander extends ChannelInboundHandlerAdapter {
    private static final int HEARTBEAT_INTERVAL = 5;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduleSendHeartBeat(ctx);
    }

    private void scheduleSendHeartBeat(ChannelHandlerContext ctx) {
        ctx.executor().scheduleAtFixedRate(()-> {
            if(ctx.channel().isActive()){
                ctx.channel().writeAndFlush(new BeatHeartRequestPacket());
            }
        },0,HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
    }
}
