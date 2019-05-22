package cn.nuaa.gcc.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * {@author: gcc}
 * {@Date: 2019/3/18 09:51}
 * {@link ByteBuffer#}
 * 关于Buffer的Scattering与Gathering
 * <p>
 * Scattering:将一个channel中的数据读到多个buffer之中，一个buffer读满了才会向第二个buffer读
 * Gathering:将数组buffer中一个一个往外channel中写
 * <p>
 * 应用场景：传输网络协议：指定好了buffer的capacity之后就可以天然的将网络协议中的header的不同的信息读到不同的buffer之中
 * 而不是说需要先一股脑地读到一个buffer之中，然后再对这个buffer进行解析和分类。
 *
 * 启动本程序，打开cmd输入nc 127.0.0.1 8899(需要预先下载netcat并配置环境变量)
 * 在cmd窗口中输入字符，回车也算一个字符
 */
public class NioTest8 {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(8899);
        serverSocketChannel.socket().bind(address);
        int messageLength = 2 + 3 + 4;

        ByteBuffer[] buffers = new ByteBuffer[3];

        buffers[0] = ByteBuffer.allocate(2);
        buffers[1] = ByteBuffer.allocate(3);
        buffers[2] = ByteBuffer.allocate(4);

        SocketChannel socketChannel = serverSocketChannel.accept();

        while (true) {
            int byteRead = 0;

            while (byteRead < messageLength) {
                long r = socketChannel.read(buffers);
                byteRead += r;

                System.out.println("byteRead:" + byteRead);

                Arrays.asList(buffers)
                        .stream()
                        .map(buffer -> "position:" + buffer.position() + ",limit:" + buffer.limit() + ",capacity:" + buffer.capacity())
                        .forEach(System.out::println);
            }
            Arrays.asList(buffers).forEach(ByteBuffer::flip);
            long byteWritten = 0;
            while (byteWritten < messageLength) {
                long r = socketChannel.write(buffers);
                byteWritten += r;
            }
            Arrays.asList(buffers).forEach(ByteBuffer::clear);
        }


    }
}
