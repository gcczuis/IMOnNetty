package cn.nuaa.gcc.im.protocol.request;

import cn.nuaa.gcc.im.protocol.Packet;
import cn.nuaa.gcc.im.protocol.command.Command;
import lombok.Data;

/**
 * {@author: gcc}
 * {@Date: 2019/4/16 09:31}
 */
@Data
public class SendToGroupRequestPacket extends Packet {
    private String groupId;
    private String message;


    @Override
    public Byte getCommand() {
        return Command.SEND_TO_GROUP_REQUEST;
    }


}
