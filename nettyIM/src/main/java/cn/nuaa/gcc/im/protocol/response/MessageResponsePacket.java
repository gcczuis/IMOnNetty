package cn.nuaa.gcc.im.protocol.response;

import cn.nuaa.gcc.im.protocol.command.Command;
import lombok.Data;
import cn.nuaa.gcc.im.protocol.Packet;

@Data
public class MessageResponsePacket extends Packet {

    private String fromUserId;

    private String fromUserName;

    private String message;

    @Override
    public Byte getCommand() {

        return Command.MESSAGE_RESPONSE;
    }
}
