package cn.nuaa.gcc.example4.util;

import cn.nuaa.gcc.example4.attribute.Attributes;
import io.netty.channel.Channel;

/**
 * {@author: gcc}
 * {@Date: 2019/4/13 20:50}
 */
public class LoginUtil {
    public static void markAsLogin(Channel channel){
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel){
        return channel.hasAttr(Attributes.LOGIN);
    }
}
