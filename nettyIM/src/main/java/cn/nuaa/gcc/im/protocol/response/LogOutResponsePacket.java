package cn.nuaa.gcc.im.protocol.response;

import cn.nuaa.gcc.im.protocol.Packet;
import cn.nuaa.gcc.im.protocol.command.Command;
import lombok.Data;

/**
 * {@author: gcc}
 * {@Date: 2019/4/15 10:13}
 */
@Data
public class LogOutResponsePacket extends Packet {
    private boolean success;
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGOUT_RESPONSE;
    }
}
