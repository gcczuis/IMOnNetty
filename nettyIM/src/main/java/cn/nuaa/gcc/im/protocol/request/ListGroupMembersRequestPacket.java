package cn.nuaa.gcc.im.protocol.request;

import cn.nuaa.gcc.im.protocol.Packet;
import cn.nuaa.gcc.im.protocol.command.Command;
import lombok.Data;

/**
 * {@author: gcc}
 * {@Date: 2019/4/16 10:54}
 */

@Data
public class ListGroupMembersRequestPacket extends Packet {
    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_REQUEST;
    }
}
