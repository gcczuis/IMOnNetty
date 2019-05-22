package cn.nuaa.gcc.im.server.handler;

import cn.nuaa.gcc.im.protocol.request.CreateGroupRequestPacket;
import cn.nuaa.gcc.im.protocol.response.CreateGroupResponsePacket;
import cn.nuaa.gcc.im.util.IDUtil;
import cn.nuaa.gcc.im.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * {@author: gcc}
 * {@Date: 2019/4/15 22:19}
 */
@ChannelHandler.Sharable
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {
    public static final CreateGroupRequestHandler INSTANCE = new CreateGroupRequestHandler();

    private CreateGroupRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket msg) throws Exception {
        List<String> userIdList = msg.getUserIdList();
        DefaultChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
        ArrayList<String> userNameList = new ArrayList<>();
        userIdList.stream().forEach(userId -> {
            //这边可能需要到数据库先查询是否有该userId，如果没有该userId则创建群聊失败，我们这里默认userId输入都正确，
            //只是可能有些userId已经下线了。
            Channel channel = SessionUtil.getChannel(userId);
            if (channel != null) {
                channelGroup.add(channel);
                userNameList.add(SessionUtil.getSession(channel).getUserName());
            }
        });
        //生成群聊标识
        String groupId = IDUtil.randomId();
        //在服务端保存群聊标识和channelGroup的一一对应关系
        SessionUtil.bindChannelGroup(groupId, channelGroup);

        CreateGroupResponsePacket groupResponsePacket = new CreateGroupResponsePacket();

        groupResponsePacket.setGroupId(groupId);
        groupResponsePacket.setSuccess(true);
        groupResponsePacket.setUserNameList(userNameList);

        channelGroup.writeAndFlush(groupResponsePacket);

        System.out.print("群创建成功，id 为[" + groupResponsePacket.getGroupId() + "], ");
        System.out.println("群里面有：" + groupResponsePacket.getUserNameList());
    }
}
