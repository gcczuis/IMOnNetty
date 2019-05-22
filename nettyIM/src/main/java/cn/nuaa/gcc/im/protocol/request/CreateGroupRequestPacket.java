package cn.nuaa.gcc.im.protocol.request;

import cn.nuaa.gcc.im.protocol.Packet;
import cn.nuaa.gcc.im.protocol.command.Command;
import lombok.Data;

import java.util.List;

/**
 * {@author: gcc}
 * {@Date: 2019/4/15 10:02}
 */
@Data
public class CreateGroupRequestPacket extends Packet {
    private List<String> userIdList;

    @Override
    public Byte getCommand() {

        return Command.CREATE_GROUP_REQUEST;
    }
}
