package cn.nuaa.gcc.im.client.Console;

import cn.nuaa.gcc.im.protocol.request.MessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * {@author: gcc}
 * {@Date: 2019/4/15 10:46}
 */
public class SendToUserConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入对方id和要发送的内容，用空格隔开：");

        MessageRequestPacket messageRequestPacket = new MessageRequestPacket();
        messageRequestPacket.setToUserId(scanner.next());
        messageRequestPacket.setMessage(scanner.next());

        channel.writeAndFlush(messageRequestPacket);
    }
}
