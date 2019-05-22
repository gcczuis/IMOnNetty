package cn.nuaa.gcc.im.protocol.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import cn.nuaa.gcc.im.protocol.Packet;

import static cn.nuaa.gcc.im.protocol.command.Command.MESSAGE_REQUEST;

@Data
@NoArgsConstructor
public class MessageRequestPacket extends Packet {
    private String toUserId;
    private String message;

    public MessageRequestPacket(String toUserId, String message) {
        this.toUserId = toUserId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
