package cn.nuaa.gcc.im.protocol.command;

public interface Command {

    Byte LOGIN_REQUEST = 1;

    Byte LOGIN_RESPONSE = 2;

    Byte MESSAGE_REQUEST = 3;

    Byte MESSAGE_RESPONSE = 4;

    Byte LOGOUT_REQUEST = 5;

    Byte LOGOUT_RESPONSE = 6;

    Byte CREATE_GROUP_REQUEST = 7;

    Byte CREATE_GROUP_RESPONSE = 8;

    Byte SEND_TO_GROUP_REQUEST = 9;

    Byte SEND_TO_GROUP_RESPONSE = 10;

    Byte LIST_GROUP_MEMBERS_REQUEST = 11;

    Byte LIST_GROUP_MEMBERS_RESPONSE = 12;

    Byte JOIN_GROUP_REQUEST = 13;

    Byte JOIN_GROUP_RESPONSE = 14;

    Byte QUIT_GROUP_REQUEST = 15;

    Byte QUIT_GROUP_RESPONSE= 16;

    Byte BEAT_HEART_REQUEST= 17;

    Byte BEAT_HEART_RESPONSE= 18;
}
