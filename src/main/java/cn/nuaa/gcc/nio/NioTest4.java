package cn.nuaa.gcc.nio;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

/**
 * {@author: gcc}
 * {@Date: 2019/3/18 09:51}
 * byteBuffer的类型化put和get
 * 注意：
 * 读取数据的时候一定要按照写入的顺序来读取，如果写入的是int而读取的是long，则一定会出现异常{@link BufferUnderflowException}
 * 因为读取的字节数一定会大于buffer中存在的字节数。
 */
public class NioTest4 {
    public static void main(String[] args) throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(513);
        buffer.putChar('c');
        buffer.putInt(11);
        buffer.putLong(5000000000L);
        buffer.putChar('我');
        buffer.putDouble(4333.4444);
        buffer.putFloat(333.11f);

        buffer.flip();

        System.out.println(buffer.getChar());
//        System.out.println(buffer.getLong());
        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getDouble());
        System.out.println(buffer.getFloat());

    }
}
