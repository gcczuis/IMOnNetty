package cn.nuaa.gcc.example4.client;

import cn.nuaa.gcc.example4.protocol.command.MessageRequestPacket;
import cn.nuaa.gcc.example4.util.LoginUtil;
import io.netty.channel.Channel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * {@author: gcc}
 * {@Date: 2019/4/13 20:58}
 */
public class ReadConsoleToServerTask implements Runnable {
    private final Channel channel;

    public ReadConsoleToServerTask(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("输入消息发送给服务端趴：");
        while (!Thread.interrupted()){
            if(LoginUtil.hasLogin(channel)){
                String message = "";
                try {
                    message = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MessageRequestPacket messageRequestPacket = new MessageRequestPacket();
                messageRequestPacket.setMessage(message);
                channel.writeAndFlush(messageRequestPacket);
            }
        }
    }
}
