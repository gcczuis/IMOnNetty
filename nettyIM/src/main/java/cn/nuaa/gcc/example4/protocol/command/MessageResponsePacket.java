package cn.nuaa.gcc.example4.protocol.command;

import lombok.Data;

/**
 * {@author: gcc}
 * {@Date: 2019/4/13 21:19}
 */
@Data
public class MessageResponsePacket extends Packet {
    private String response;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
