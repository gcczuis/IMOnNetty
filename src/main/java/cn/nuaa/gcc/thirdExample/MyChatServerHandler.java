package cn.nuaa.gcc.thirdExample;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * {@author: gcc}
 * {@Date: 2019/3/16 11:11}
 * 需求：
 * 服务器首先启动，然后会有n个客户端与服务器建立连接，当第一个客户端与服务器建立连接则无事发生，之后的客户端与服务端
 * 建立连接，则服务端会给其他没下线的客户端发送【xxx上线】，如果某个客户端与服务端失去连接，服务端则会向其余没下线的客户端发送【xxx下线】，如果a,b,c都在线，
 * 则a如果给服务端发送一条信息，则b,c都能收到这条消息。
 */
public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {
    //用于保存已经连接的channel对象
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.forEach(ch -> {
            if (ch != channel) {
                ch.writeAndFlush(channel.remoteAddress() + " 发送的消息：" + msg + "\n");
            } else {
                ch.writeAndFlush("【自己】：" + msg + "\n");
            }
        });

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //底层遍历了所有存入的channel并调用channel的writeAndFlush
        channelGroup.writeAndFlush("【服务器】-" + channel.remoteAddress() + "加入\n");
        //先广播再加入，防止信息发给刚上线的channel
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //注意当连接一旦断掉，channelGroup会自动将该断掉的channel移除出去
        channelGroup.writeAndFlush("【服务器】-" + channel.remoteAddress() + "离开\n");

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + "上线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + "下线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();

    }
}
