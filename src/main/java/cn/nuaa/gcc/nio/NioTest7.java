package cn.nuaa.gcc.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * {@author: gcc}
 * {@Date: 2019/3/18 09:51}
 * {@link MappedByteBuffer}
 */
public class NioTest7 {
    public static void main(String[] args) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("NioTest7.txt","rw");
        FileChannel channel = randomAccessFile.getChannel();
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedByteBuffer.put(0,(byte)'a');
       mappedByteBuffer.put(3,(byte)'b');

        randomAccessFile.close();


    }
}
