package cn.nuaa.gcc.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.SecureRandom;

/**
 * {@author: gcc}
 * {@Date: 2019/3/18 09:51}
 * apiNote:
 * {@link Buffer#clear()}clear方法并不是真正的将buffer内部数据清空+各个数组指针回归初始值，而只是将limit设为capacity，
 * position设为0。这样通过put操作，可以将原来的值覆盖掉，来实现一种clear的效果。（是为了供读取channel数据以及相对的put操作所设）
 * <p>
 * 错误：如果将下面代码中{@code byteBuffer.clear()}注释掉会打印：
 * 1024
 * 0
 * 0
 * 0
 * 0
 * 0
 * 。。。
 * <p>
 * 解析：运行到{@code outputChannne.write(byteBuffer)}代码时position = limit = capacity = 1024;这时没有
 *      {@code clear()}方法，然后执行{@code inputChannel.read(byteBuffer)},自然读不到任何数据返回0。然后
 *      {@cdde flip()}方法调用，一直重复输出之前1024个字节中读取到的内容。
 */
public class NioTest3 {

    public static void main(String[] args) throws IOException {
        try(FileInputStream fileInputStream = new FileInputStream("input.txt");
            FileOutputStream fileOutputStream = new FileOutputStream("output.txt")) {

            FileChannel outputChannne = fileOutputStream.getChannel();
            FileChannel inputChannel = fileInputStream.getChannel();

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            while (true) {
                byteBuffer.clear();
                int read = inputChannel.read(byteBuffer);
                System.out.println(read);
                if (-1 == read) {
                    break;
                }
                byteBuffer.flip();
                outputChannne.write(byteBuffer);
            }
        }

    }

}

