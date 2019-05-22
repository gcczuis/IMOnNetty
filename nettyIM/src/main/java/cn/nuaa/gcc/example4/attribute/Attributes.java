package cn.nuaa.gcc.example4.attribute;

import io.netty.util.AttributeKey;

/**
 * {@author: gcc}
 * {@Date: 2019/4/13 20:45}
 */
public interface Attributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
}
