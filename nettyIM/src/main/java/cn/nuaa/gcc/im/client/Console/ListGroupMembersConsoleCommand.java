package cn.nuaa.gcc.im.client.Console;

import cn.nuaa.gcc.im.protocol.request.ListGroupMembersRequestPacket;
import cn.nuaa.gcc.im.util.SessionUtil;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * {@author: gcc}
 * {@Date: 2019/4/16 10:44}
 */
public class ListGroupMembersConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入群id:");
        String groupId = scanner.next();

        if (!SessionUtil.hasGroupId(groupId, channel)) {
            System.out.println("您不是该群聊成员，没有权限查看群成员。。。。");
            return;
        }

        ListGroupMembersRequestPacket groupMembersRequestPacket = new ListGroupMembersRequestPacket();
        groupMembersRequestPacket.setGroupId(groupId);

        channel.writeAndFlush(groupMembersRequestPacket);

    }
}
