package cn.nuaa.gcc.im.client.Console;

import cn.nuaa.gcc.im.protocol.request.QuitGroupRequestPacket;
import cn.nuaa.gcc.im.util.SessionUtil;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * {@author: gcc}
 * {@Date: 2019/4/16 15:24}
 */
public class QuitGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入您要退出的群的groupId:");
        String groupId = scanner.next();

        if(!SessionUtil.hasGroupId(groupId,channel)){
            System.out.println("您本来就不在该群中！请输入其他命名");
            return;
        }

        QuitGroupRequestPacket quitGroupRequestPacket = new QuitGroupRequestPacket();
        quitGroupRequestPacket.setGroupId(groupId);

        channel.writeAndFlush(quitGroupRequestPacket);
    }
}
