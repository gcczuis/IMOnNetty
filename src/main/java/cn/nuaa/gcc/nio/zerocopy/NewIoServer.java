package cn.nuaa.gcc.nio.zerocopy;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * {@author: gcc}
 * {@Date: 2019/3/21 15:09}
 */
public class NewIoServer {
    public static void main(String[] args) throws Exception{
        InetSocketAddress address = new InetSocketAddress(8899);

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(address);

        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);

        while (true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            //其实不指定也是阻塞的
            socketChannel.configureBlocking(true);

            int readCount = 0;

            while(-1 != readCount){
                readCount = socketChannel.read(byteBuffer);
                System.out.println(readCount);
                byteBuffer.rewind();
            }
        }

    }
}
