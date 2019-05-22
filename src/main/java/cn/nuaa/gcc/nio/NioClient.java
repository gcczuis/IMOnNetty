package cn.nuaa.gcc.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * {@author: gcc}
 * {@Date: 2019/3/19 00:11}
 */
public class NioClient {
    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        Socket socket = socketChannel.socket();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8899));

        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);

        while (true) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isConnectable()) {
                    SocketChannel channel = (SocketChannel) key.channel();

                    /*If this channel is already connected then this method will not block and
                        will immediately return true.

                      If this channel is in non-blocking mode then this method will return false
                          if the connection process is not yet complete.

                      If this channel is in blocking mode then this method will block until the connection
                            either completes or fails, and will always either return true or throw a checked
                            exception describing the failure.*/
                    while (!channel.finishConnect()) {
                        Thread.sleep(1000);
                    }
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    readBuffer.put((LocalDateTime.now() + " 连接成功").getBytes());
                    readBuffer.flip();
                    channel.write(readBuffer);

                    ExecutorService service = Executors.newSingleThreadExecutor();

                    service.submit(() -> {
                        while (true) {
                            BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(System.in)));
                            try {
                                String str = bufferedReader.readLine();
                                readBuffer.rewind();
                                readBuffer.put(str.getBytes());
                                readBuffer.flip();
                                channel.write(readBuffer);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    channel.register(selector,SelectionKey.OP_READ);
                }else if(key.isReadable()){
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    int read = channel.read(readBuffer);
                    if(read > 0){
                        System.out.println(new String(readBuffer.array(),0,read));
                    }
                }
            }
            keys.clear();


        }


    }
}
