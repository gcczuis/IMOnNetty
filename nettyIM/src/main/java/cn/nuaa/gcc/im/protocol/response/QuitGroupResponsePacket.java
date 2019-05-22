package cn.nuaa.gcc.im.protocol.response;

import cn.nuaa.gcc.im.protocol.Packet;
import cn.nuaa.gcc.im.protocol.command.Command;
import lombok.Data;

/**
 * {@author: gcc}
 * {@Date: 2019/4/16 15:32}
 */
@Data
public class QuitGroupResponsePacket extends Packet {
    private boolean success;
    private String reason;
    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.QUIT_GROUP_RESPONSE;
    }
}
