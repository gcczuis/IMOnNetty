package cn.nuaa.gcc.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * {@author: gcc}
 * {@Date: 2019/3/18 22:55}
 */
public class NioTest9 {
    public static void main(String[] args) throws IOException {
        int[] ports = new int[5];
        ports[0] = 5000;
        ports[1] = 5001;
        ports[2] = 5002;
        ports[3] = 5003;
        ports[4] = 5004;
        Selector selector = Selector.open();
        for (int i = 0; i < ports.length; i++) {
            //ServerSocketChannel是服务端专门用来监听客户端连接的对应于ServerSocket,
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);

            ServerSocket serverSocket = serverSocketChannel.socket();
            serverSocket.bind(new InetSocketAddress(ports[i]));

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("监听 " + ports[i] + " 端口");
        }

        //上述是将一系列io监听任务绑定到selector上，
        while (true) {
            //该方法是阻塞方法，直到有监听事件发生，things是事件个数
            int things = selector.select();
            System.out.println("event num:"+things);

            Set<SelectionKey> keys = selector.selectedKeys();
            System.out.println("selected keys:"+keys);
            Iterator<SelectionKey> iter = keys.iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                if (key.isAcceptable()) {
                    //看上面的key.isAcceptable()所知这个key一定是关联到serverSocketChannel，因为只有serverSocketChannel
                    //是监听连接接入事件，所以这个key也一定是关联到serverSocketChannel(上面设置的)，所以可以强转成
                    // serverSocketChannel
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    //由于设置了configureBlocking(false)所以如果没有连接进入会立即返回null，但是这里key.isAcceptable()表明
                    //已经有连接进入，所以直接返回socketChannel对象
                    SocketChannel socketChannel = channel.accept();

                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    System.out.println("获得客户端连接："+socketChannel);
                } else if (key.isReadable()) {
                    //由于上面进入key.isReadable()，明显是对应着socketChannel.register
                    // (selector, SelectionKey.OP_READ);所以可以强转成SocketChannel
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    int byteRead = 0;
                    while (true) {
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int read = socketChannel.read(buffer);
                        if (read <= 0) {
                            break;
                        }
                        byteRead += read;
                        buffer.flip();
                        //回写回去
                        socketChannel.write(buffer);
                    }
                    System.out.println("读取: " + byteRead + "个字节,来自于" + socketChannel);
                }
            }
            //非常重要，将处理完成的事件删除掉。
            keys.clear();
        }
    }
}
