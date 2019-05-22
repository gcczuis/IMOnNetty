package cn.nuaa.gcc.bytebuf;

import io.netty.buffer.*;

import java.util.Iterator;

/**
 * {@author: gcc}
 * {@Date: 2019/3/31 10:40}
 * netty中ByteBuf所提供的三种缓冲区类型：
 * 1. heap buffer(使用最多)
 *    这是最常用的类型，ByteBuf将数据存储到JVM的堆空间中，并且将实际的数据存放到byte array中来实现。
 *    优点：由于数据是存储在JVM的堆中，因此可以快速的创建与快速的释放，并且它提供了直接访问内部字节数组的方法（array()）.
 *    缺点：每次读写数据时，都需要先将数据复制到直接缓冲区中再进行网络传输。
 *
 * 2. direct buffer（内存并不存在于jvm）
 *    在堆外直接分配内存空间，直接缓冲区并不会占用堆的容量空间，因为它是由操作系统在本地内存进行的数据分配。
 *    优点：在使用Socket进行数据传递时，性能非常好，因为数据直接位于操作系统的本地内存中，所以不需要从JVM将数据复制到直接缓冲区中，性能好。
 *    缺点：因为Direct Buffer是直接在操作系统内存中的，所以内存空间的分配与释放要比堆空间更加复杂，而且速度要慢一点
 *         Netty通过提供内存池来解决这个问题。直接缓冲区并不支持通过字节数组的方式来访问数据(buf.hasArray())。
 *
 * 重点：对于后端的业务消息的编解码来说，推荐使用HeapByteBuf;对于I/O通信线程来读写缓冲区时，推荐使用DirectByteBuf,因为涉及到数据的网络传递，用zero-copy可以减少一次内存的拷贝。
 *
 * 3. composite buffer（复合缓冲区，看做一种容器，能容纳上面两种缓冲区）
 *      应用：在http开发中可能浏览器发送给我们head和body，我们可以将这两个封装成两个buffer再放入composite buffer中。以一种一致的方式来对其进行操作或者传递。
 *
 *
 * JDK的ByteBuffer与Netty的ByteBuf之间的差异对比：
 * 1.Netty的ByteBuf采用了读写索引分离的策略(readerIndex与writerIndex),一个初始化（里面尚未有任何数据）的ByteBuf的readerIndex与writerIndex为0。
 * 2.当读索引与写索引处于同一个位置，如果我们继续读取，那么就会抛出{@link IndexOutOfBoundsException}
 * 3.对于ByteBuf的任何读写操作都会单独维护读索引与写索引。maxCapacity最大容量默认的限制就是Integer.MAX_VALUE
 *
 * JDK的ByteBuffer的缺点：
 * 1. final byte[] hb;这是JDK的ByteBuffer对象中用于存储数据的对象声明；可以看到，其字节数组是被声明为final的，也就是长度是固定不变的，一旦分配好后不能动态扩容与
 * 收缩；而且当待存储的数据字节很大时就很有可能出现{@link IndexOutOfBoundsException},如果要预防这个异常，那需要在存储之前完全确定好待存储的字节大小。如果
 * ByteBuffer的空间不足，我们只有一种解决方案：创建一个全新的ByteBuffer对象,然后再将之前的ByteBuffer中数据复制过去，这一切操作都需要由开发者自己来手动完成。
 *
 * 2.ByteBuffer只使用一个position指针来标识位置信息，在进行读写切换时就需要调用flip方法或是rewind方法，使用起来很不方便。
 *
 * netty的ByteBuf优点：
 * 1.存储字节是数组是动态的，其最大值默认是Integer.MAX_VALUE。这里的动态性是体现在write方法中的，write方法在执行时会判断buffer容量，如果不足则自动扩容。
 * 2.ByteBuf的读写索引是完全分开的，使用起来就很方便。
 */
public class ByteBufTest1 {
    public static void main(String[] args){
        CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();
        ByteBuf heapBuf = Unpooled.buffer(10);
        ByteBuf directBuffer = Unpooled.directBuffer(8);

        compositeByteBuf.addComponents(heapBuf,directBuffer);
//        compositeByteBuf.removeComponent(0);

        /*Iterator<ByteBuf> iterator = compositeByteBuf.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }*/
        compositeByteBuf.forEach(System.out::println);
    }
}
