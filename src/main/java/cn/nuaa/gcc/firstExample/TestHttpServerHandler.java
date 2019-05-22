package cn.nuaa.gcc.firstExample;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * {@author: gcc}
 * {@Date: 2019/3/16 09:29}
 * 现象：
 * 我们使用火狐浏览器访问http://localhost:8899/地址，在控制台打印了两行"请求方法名：GET"
 * 原因：
 * 浏览器在访问的时候同时也会请求/favicon.ico请求网站图标，但是netty是没有网址路由的，所以都会由下面这个
 * handler处理，所以会打印出来两个。
 * <p>
 * 现象：输出：
 * handlerAdded
 * channelRegistered
 * channelActive
 * 请求方法名：GET
 * handlerAdded
 * channelRegistered
 * channelActive
 * 请求方法名：GET
 * 请求favicon.ico
 * <p>
 * 等待3分钟打印：
 * channelInactive
 * channelUnregistered
 * handlerRemoved
 * 关闭浏览器后再打印：
 * channelInactive
 * channelUnregistered
 * handlerRemoved
 * <p>
 * 原因：
 * 浏览器是有超时时间的，在超时时间过后连接就断了，所以就inactive接着unregister了
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    //读取客户端发过来的请求，并向客户端返回响应
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest) {
            System.out.println(ctx.channel().remoteAddress());
            HttpRequest httpRequest = (HttpRequest) msg;
            System.out.println("请求方法名：" + httpRequest.method());
            URI uri = new URI(httpRequest.uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("请求favicon.ico");
                return;
            }
            ByteBuf content = Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8);
            //指定http协议的版本，返回的状态码是ok（200），返回的内容
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.OK, content);
            //指定返回类型和内容长度
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
            ctx.writeAndFlush(response);
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded");
        super.handlerAdded(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved");
        super.handlerRemoved(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelRegistered");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelUnregistered");
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
