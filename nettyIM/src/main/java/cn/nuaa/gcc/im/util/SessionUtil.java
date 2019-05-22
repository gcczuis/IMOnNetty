package cn.nuaa.gcc.im.util;

import cn.nuaa.gcc.im.session.Session;
import io.netty.channel.Channel;
import cn.nuaa.gcc.im.attribute.Attributes;
import io.netty.channel.group.ChannelGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtil {
    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();
    private static final Map<String, ChannelGroup> groupIdChannelGroupMap = new ConcurrentHashMap<>();


    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    public static boolean hasLogin(Channel channel) {

        return channel.attr(Attributes.SESSION).get() != null;
    }

    public static Session getSession(Channel channel) {

        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String userId) {

        return userIdChannelMap.get(userId);
    }

    public static void bindChannelGroup(String groupId, ChannelGroup channelGroup) {
        groupIdChannelGroupMap.put(groupId, channelGroup);
    }

    public static ChannelGroup getChannelGroup(String groupId) {
        return groupIdChannelGroupMap.get(groupId);
    }

    //给客户端的channel绑定的groupIdList加一个groupId
    public static void addChannelGroup(String groupId, Channel channel) {
        List<String> list = channel.attr(Attributes.groupIdList).get();
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(groupId);
        channel.attr(Attributes.groupIdList).set(list);
    }

    public static boolean hasGroupId(String groupId, Channel channel) {
        List<String> list = channel.attr(Attributes.groupIdList).get();
        return list.contains(groupId);
    }


}
