package cn.nuaa.gcc.im.client.Console;


import cn.nuaa.gcc.im.protocol.request.LoginRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * {@author: gcc}
 * {@Date: 2019/4/15 10:29}
 */
public class LoginConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入用户名和密码，中间用逗号隔开：");

        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        String next = scanner.next();
        loginRequestPacket.setUserName(next.split(",")[0]);
        loginRequestPacket.setPassword(next.split(",")[1]);

        channel.writeAndFlush(loginRequestPacket);

        //等待服务器的登陆响应
        waitForLoginResponse();
    }

    private void waitForLoginResponse() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
