package cn.nuaa.gcc.nio;

import java.nio.ByteBuffer;

/**
 * {@author: gcc}
 * {@Date: 2019/3/18 09:51}
 * {@link ByteBuffer#asReadOnlyBuffer()}只读buffer,在作为方法返回的时候是很有用的，不允许调用端去修改原来的buffer底层数组。
 * 我们可以随时将一个普通Buffer调用asReadOnlyBuffer方法返回一个只读Buffer但不能将一个只读Buffer转换为读写Buffer
 */
public class NioTest6 {
    public static void main(String[] args) throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        //class java.nio.HeapByteBufferR
        System.out.println(readOnlyBuffer.getClass());
        //java.nio.ReadOnlyBufferException
        readOnlyBuffer.put((byte)1);


    }
}
