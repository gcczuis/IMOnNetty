package cn.nuaa.gcc.example3.util;

import cn.nuaa.gcc.example3.attribute.Attributes;
import io.netty.channel.Channel;

/**
 * {@author: gcc}
 * {@Date: 2019/4/13 20:50}
 */
public class LoginUtil {
    public static void markAsLogin(Channel channel){
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hansLogin(Channel channel){
        return channel.hasAttr(Attributes.LOGIN);
    }
}
