package cn.nuaa.gcc.im.client.Console;

import cn.nuaa.gcc.im.protocol.request.JoinGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * {@author: gcc}
 * {@Date: 2019/4/16 11:24}
 */
public class JoinGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请您输入要加入群聊的groupId");

        JoinGroupRequestPacket joinGroupRequestPacket = new JoinGroupRequestPacket();
        String groupId = scanner.next();
        joinGroupRequestPacket.setGroupId(groupId);

        channel.writeAndFlush(joinGroupRequestPacket);
    }
}
