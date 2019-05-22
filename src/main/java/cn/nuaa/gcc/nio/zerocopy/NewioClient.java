package cn.nuaa.gcc.nio.zerocopy;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * {@author: gcc}
 * {@Date: 2019/3/21 15:16}
 *
 * todo 这里的错误没有解决，最多存储数据一直是8M
 */
public class NewioClient {
    public static void main(String[] args) throws Exception{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",8899));
        socketChannel.configureBlocking(true);

        String filename = "C:\\Users\\user\\Desktop\\tess.mp4";
        FileChannel fileChannel = new FileInputStream(filename).getChannel();

        long startTime = System.currentTimeMillis();
        System.out.println(fileChannel.size());
        System.out.println("开始传输");
        long transfercount = fileChannel.transferTo(0L, fileChannel.size(), socketChannel);

        System.out.println("发送总字节数： "+transfercount+"，耗时："+(System.currentTimeMillis()-startTime));

        fileChannel.close();

    }
}
