package cn.nuaa.gcc.im.protocol.response;

import cn.nuaa.gcc.im.protocol.Packet;
import cn.nuaa.gcc.im.protocol.command.Command;
import lombok.Data;

/**
 * {@author: gcc}
 * {@Date: 2019/4/16 09:36}
 */
@Data
public class SendToGroupResponsePacket extends Packet {
    private boolean success;
    private String reason;
    private String message;


    @Override
    public Byte getCommand() {
        return Command.SEND_TO_GROUP_RESPONSE;
    }
}
