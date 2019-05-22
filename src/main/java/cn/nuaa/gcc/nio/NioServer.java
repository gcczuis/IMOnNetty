package cn.nuaa.gcc.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 实现了一个简单的消息群发的功能
 * {@author: gcc}
 * {@Date: 2019/3/18 23:42}
 */
public class NioServer {

    private static Set<SocketChannel> SocketChannels = new HashSet<>();

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(8899));

        Selector selector = Selector.open();

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            int select = selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();

                if (key.isAcceptable()) {
                    try {
                        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                        SocketChannel client = channel.accept();
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);
                        System.out.println("新连接接入：" + client);
                        SocketChannels.add(client);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else if (key.isReadable()) {
                    try {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int count = client.read(buffer);
                        if (count > 0) {
                            buffer.flip();
                            //要记住指定buffer读取的长度哦（count）
                            String receiveString = new String(buffer.array(),0,count);
                            System.out.println(client + ": " + receiveString);
                            //转发数据（没有考虑当有一个连接断掉后，SocketChannels删除该连接的情况，所以这只是个简单的实现）
                            SocketChannels.forEach(channel -> {
                                ByteBuffer buffer1 = ByteBuffer.allocate(1024);
                                buffer1.put((channel + ": " + receiveString).getBytes());
                                buffer1.flip();
                                try {
                                    channel.write(buffer1);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
            selectionKeys.clear();
        }
    }
}
