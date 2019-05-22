package cn.nuaa.gcc.im.client.Console;

import cn.nuaa.gcc.im.protocol.request.LogOutRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * {@author: gcc}
 * {@Date: 2019/4/15 10:38}
 */
public class LogOutConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        LogOutRequestPacket logOutRequestPacket = new LogOutRequestPacket();
        channel.writeAndFlush(logOutRequestPacket);
    }
}
