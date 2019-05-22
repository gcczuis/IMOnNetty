package cn.nuaa.gcc.im.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import io.netty.buffer.ByteBuf;
import lombok.Data;

@Data
public abstract class Packet {
    /**
     * 协议版本,由于协议版本不是放在协议中的【数据】部分的，所以不需要被序列化
     * 在{@link PacketCodeC#encode(ByteBuf, Packet)}中手动设置
     */
    @JSONField(deserialize = false, serialize = false)
    private Byte version = 1;

    /**
     * 指令
     * 因为我们的协议里面，command 这个字段已经有了，所以不需要将该字段序列化进去啦，和 version 一样。这里由于不存在实际的 command 成员变量，所以不需要考虑反序列化
     */
    @JSONField(serialize = false)
    public abstract Byte getCommand();
}
