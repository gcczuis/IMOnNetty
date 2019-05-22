package cn.nuaa.gcc.im.attribute;

import cn.nuaa.gcc.im.session.Session;
import io.netty.util.AttributeKey;

import java.util.List;

public interface Attributes {
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
    AttributeKey<List<String>> groupIdList = AttributeKey.newInstance("groupIdList");
}
