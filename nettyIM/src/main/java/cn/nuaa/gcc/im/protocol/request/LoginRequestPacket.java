package cn.nuaa.gcc.im.protocol.request;

import lombok.Data;
import cn.nuaa.gcc.im.protocol.Packet;

import static cn.nuaa.gcc.im.protocol.command.Command.LOGIN_REQUEST;

@Data
public class LoginRequestPacket extends Packet {
    private String userName;

    private String password;

    @Override
    public Byte getCommand() {

        return LOGIN_REQUEST;
    }
}
