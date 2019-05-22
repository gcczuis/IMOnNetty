package cn.nuaa.gcc.example3.serialize.impl;

import cn.nuaa.gcc.example3.serialize.Serializer;
import cn.nuaa.gcc.example3.serialize.SerializerAlogrithm;
import com.alibaba.fastjson.JSON;

public class JSONSerializer implements Serializer {

    @Override
    public byte getSerializerAlogrithm() {
        return SerializerAlogrithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {

        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {

        return JSON.parseObject(bytes, clazz);
    }
}
