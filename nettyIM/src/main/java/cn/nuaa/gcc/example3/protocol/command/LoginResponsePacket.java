package cn.nuaa.gcc.example3.protocol.command;

import lombok.Data;

/**
 * {@author: gcc}
 * {@Date: 2019/4/13 17:49}
 */
@Data
public class LoginResponsePacket extends Packet {
    private boolean success;
    private String reason;
    @Override
    public Byte getCommand() {
        return 2;
    }
}
