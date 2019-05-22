package cn.nuaa.gcc.im.protocol.response;

import cn.nuaa.gcc.im.protocol.Packet;
import cn.nuaa.gcc.im.protocol.command.Command;

/**
 * {@author: gcc}
 * {@Date: 2019/4/17 16:18}
 */
public class BeatHeartResponsePacket extends Packet {

    @Override
    public Byte getCommand() {
        return Command.BEAT_HEART_RESPONSE;
    }
}
