package cn.nuaa.gcc.im.client.Console;

import cn.nuaa.gcc.im.protocol.request.CreateGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Arrays;
import java.util.Scanner;

/**
 * {@author: gcc}
 * {@Date: 2019/4/15 10:49}
 */
public class CreateGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入您想创建群聊时拉入的成员的userId，用逗号隔开：");

        CreateGroupRequestPacket groupRequestPacket = new CreateGroupRequestPacket();
        groupRequestPacket.setUserIdList(Arrays.asList(scanner.next().split(",")));

        channel.writeAndFlush(groupRequestPacket);
    }
}
