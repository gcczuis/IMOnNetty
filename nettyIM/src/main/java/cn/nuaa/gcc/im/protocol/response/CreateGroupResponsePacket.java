package cn.nuaa.gcc.im.protocol.response;

import cn.nuaa.gcc.im.protocol.Packet;
import cn.nuaa.gcc.im.protocol.command.Command;
import lombok.Data;

import java.util.List;


/**
 * {@author: gcc}
 * {@Date: 2019/4/15 10:14}
 */
@Data
public class CreateGroupResponsePacket extends Packet {
    //创建成功与否
    private boolean success;
    private String reason;

    //一些基本信息
    private String groupId;
    private List<String> userNameList;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_RESPONSE;
    }
}
