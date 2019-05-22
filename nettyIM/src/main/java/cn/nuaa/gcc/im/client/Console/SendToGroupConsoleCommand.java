package cn.nuaa.gcc.im.client.Console;

import cn.nuaa.gcc.im.attribute.Attributes;
import cn.nuaa.gcc.im.protocol.request.SendToGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.List;
import java.util.Scanner;

/**
 * {@author: gcc}
 * {@Date: 2019/4/16 09:28}
 */
public class SendToGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入您想发送消息的groupId和消息内容，中间用冒号隔开：");

        String next = scanner.next();
        String groupId = next.split(":")[0];
        String message = next.split(":")[1];

        List<String> groupIdList = channel.attr(Attributes.groupIdList).get();
        if(!groupIdList.contains(groupId)){
            System.out.println("您还不是该群组的成员，请先创建群组或者加入该群组");
            return;
        }

        SendToGroupRequestPacket toGroupRequestPacket = new SendToGroupRequestPacket();
        toGroupRequestPacket.setGroupId(groupId);
        toGroupRequestPacket.setMessage(message);

        channel.writeAndFlush(toGroupRequestPacket);

    }
}
