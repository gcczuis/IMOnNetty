package cn.nuaa.gcc.im.client.Console;

import cn.nuaa.gcc.im.util.SessionUtil;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * {@author: gcc}
 * {@Date: 2019/4/15 11:01}
 */
public class ConsoleCommandManager implements ConsoleCommand {
    private static final Map<String,ConsoleCommand> map = new HashMap<>();
    static {
        map.put("login",new LoginConsoleCommand());
        map.put("logOut",new LogOutConsoleCommand());
        map.put("sendMessage",new SendToUserConsoleCommand());
        map.put("createGroup",new CreateGroupConsoleCommand());
        map.put("sendToGroup",new SendToGroupConsoleCommand());
        map.put("listGroupMembers",new ListGroupMembersConsoleCommand());
        map.put("joinGroup",new JoinGroupConsoleCommand());
        map.put("quitGroup",new QuitGroupConsoleCommand());
    }


    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("现有系统支持的操作如下:\n1.login:登录\n2.logOut:登出\n3.sendMessage:向某人发送消息\n4.createGroup:创建群聊天\n5.listGroupMembers:显示群成员\n6.sendToGroup,发送群聊天内容\n7.joinGroup:加入群聊\n8.quitGroup:退出群聊\n请输入您想进行的操作:\n");
        do {
            String operation = scanner.next();
            ConsoleCommand consoleCommand = map.get(operation);
            if(consoleCommand == null){
                System.out.println(operation+"不被支持，请重新输入命令：");
            }else{
                if (consoleCommand instanceof LoginConsoleCommand) {
                    if(SessionUtil.hasLogin(channel)){
                        System.out.println("您已经登录了，请不要重复登录，请继续输入命令：");
                    }else{
                        consoleCommand.exec(scanner,channel);
                    }
                }else{
                    if(SessionUtil.hasLogin(channel)){
                        consoleCommand.exec(scanner,channel);
                    }else{
                        System.out.println("您还未登陆，"+operation+"操作不被支持，请输入登陆命令：");
                    }
                }
            }
        }while (true);
    }

}
