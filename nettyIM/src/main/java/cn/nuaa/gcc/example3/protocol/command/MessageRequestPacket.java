package cn.nuaa.gcc.example3.protocol.command;

import lombok.Data;

/**
 * {@author: gcc}
 * {@Date: 2019/4/13 21:15}
 */
@Data
public class MessageRequestPacket extends Packet {
    private String message;
    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
