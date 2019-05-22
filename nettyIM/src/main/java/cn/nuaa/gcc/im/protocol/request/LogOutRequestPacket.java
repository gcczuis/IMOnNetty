package cn.nuaa.gcc.im.protocol.request;


import cn.nuaa.gcc.im.protocol.Packet;
import cn.nuaa.gcc.im.protocol.command.Command;

/**
 * {@author: gcc}
 * {@Date: 2019/4/15 09:59}
 */
public class LogOutRequestPacket extends Packet {
    //并不需要其他字段，服务端接收到这个请求后可以通过channel和userid的映射关系将该用户下线
    @Override
    public Byte getCommand() {
        return Command.LOGOUT_REQUEST;
    }
}
