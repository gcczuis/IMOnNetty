package cn.nuaa.gcc.im.protocol.request;

import cn.nuaa.gcc.im.protocol.Packet;
import cn.nuaa.gcc.im.protocol.command.Command;

/**
 * {@author: gcc}
 * {@Date: 2019/4/17 16:17}
 */
public class BeatHeartRequestPacket extends Packet {

    @Override
    public Byte getCommand() {
        return Command.BEAT_HEART_REQUEST;
    }
}
