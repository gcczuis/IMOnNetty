package cn.nuaa.gcc.im.client.Console;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * {@author: gcc}
 * {@Date: 2019/4/15 09:57}
 */
public interface ConsoleCommand {

    void exec(Scanner scanner, Channel channel);

}
