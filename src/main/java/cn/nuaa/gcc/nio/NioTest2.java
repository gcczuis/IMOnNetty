package cn.nuaa.gcc.nio;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * {@author: gcc}
 * {@Date: 2019/3/18 09:51}
 */
public class NioTest2 {
    public static void main(String[] args) throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(513);
        try(FileInputStream inputStream = new FileInputStream("NioTest2.txt")){
            FileChannel channel = inputStream.getChannel();
            channel.read(buffer);
            buffer.flip();
            while (buffer.hasRemaining()) {
                System.out.println((char) buffer.get());
            }
        }
    }
}
