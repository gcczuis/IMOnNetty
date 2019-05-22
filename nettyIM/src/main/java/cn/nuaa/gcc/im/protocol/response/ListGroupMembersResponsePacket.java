package cn.nuaa.gcc.im.protocol.response;

import cn.nuaa.gcc.im.protocol.Packet;
import cn.nuaa.gcc.im.protocol.command.Command;
import lombok.Data;

import java.util.List;

/**
 * {@author: gcc}
 * {@Date: 2019/4/16 10:56}
 */
@Data
public class ListGroupMembersResponsePacket extends Packet {
    private boolean success;
    private String reason;
    private String groupId;
    private List<String> memberList;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_RESPONSE;
    }
}
