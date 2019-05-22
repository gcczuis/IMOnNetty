package cn.nuaa.gcc.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * 一种支持对0个或者多个字节进行随机（类似ByteBuffer中的绝对操作）和顺序（类似ByteBuffer中的相对操作）访问的对象
 *
 *
 * <h3>buffer的创建</h3>
 *
 * 推荐使用{@link Unpooled}中的辅助方法来创建一个新的buffer而不是调用具体实现中的构造方法
 *
 * <h3>随机访问索引</h3>
 *
 * 跟原生字节数组一样，{@link ByteBuf}是以0为起始索引。这意味着buffer中的第一个字节的索引是{@code 0}而最后一个字节的索引总是
 * {@link ByteBuf#capacity() - 1},所以如果你想遍历一个buffer，你可以这样做：
 *
 * <pre>
 * {@link ByteBuf} buffer = ...;
 * for (int i = 0; i &lt; buffer.capacity(); i ++) {
 *     byte b = buffer.getByte(i);
 *     System.out.println((char) b);
 * }
 * </pre>
 *
 * <h3>顺序访问索引</h3>
 *
 * {@link ByteBuf} 提供了两种指针变量来支持顺序话的读写操作- {@link ByteBuf#readerIndex() readerIndex}来支持读操作
 * -{@link ByteBuf#writerIndex() writerIndex} 来支持写操作。下面的图展示了一个buffer如何被这两个指针分割成3个区域：
 *
 * <pre>
 *      +-------------------+------------------+------------------+
 *      | discardable bytes |  readable bytes  |  writable bytes  |
 *      |                   |     (CONTENT)    |                  |
 *      +-------------------+------------------+------------------+
 *      |                   |                  |                  |
 *      0      <=      readerIndex   <=   writerIndex    <=    capacity
 * </pre>
 *
 * <h4>可读字节（实际内容）</h4>
 *
 * 此部分是实际数据存储的地方。任何以{@code read}或者{@code skip}开头的函数操作会获得或者跳过该
 * {@link ByteBuf#readerIndex() readerIndex}所指向的数据并且该索引会增加对应的数量。
 * 如果一个读操作的参数也是一个{@link ByteBuf}而且该参数的目的索引没有指定，那么该参数的
 * {@link ByteBuf#writerIndex() writerIndex}也会跟着源ByteBuf的readerIndex一起增加。
 * This segment is where the actual data is stored.  Any operation whose name
 * <p>
 * 如果没有足够的空间剩余。会抛出{@link IndexOutOfBoundsException}，新分配或者包装或者复制的buffer的readerIndex为{@code 0}
 *
 * <pre>
 * // 迭代buffer的所有可读字节.
 * {@link ByteBuf} buffer = ...;
 * while (buffer.isReadable()) {
 *     System.out.println(buffer.readByte());
 * }
 * </pre>
 *
 * <h4>可写字节</h4>
 *
 * 这块区域是一个需要被填充的未定义的空间。任何以{@code write}开头的函数操作都会在{@link ByteBuf#writerIndex() writerIndex}索
 * 引处写入数据同时将writerIndex增加对应的数量。如果写操作的参数也是一个{@link ByteBuf}，那么该参数的readerIndex也会和目标buffer
 * 的writerIndex一起增加。
 * <p>
 * 如果没有足够的剩余可写字节空间，会抛出{@link IndexOutOfBoundsException}，新分配的buffer的writerIndex的默认值是{@code 0}.
 * 包装或者拷贝的buffer的writerIndex的默认值是该buffer的capacity。
 *
 * <pre>
 * // 用随机的整形数值填充满该buffer的可写字节空间
 * {@link ByteBuf} buffer = ...;
 * while (buffer.maxWritableBytes() >= 4) {
 *     buffer.writeInt(random.nextInt());
 * }
 * </pre>
 *
 * <h4>丢弃的字节</h4>
 *
 * 该部分包含了我们通过读操作读取的所有已读字节。初始时，该部分的size为{@code 0},但当读操作被执行该部分的尺寸慢慢会增加到writerIndex，
 * 像下面的图片描绘的那样，我们可以通过调用{@link ByteBuf#discardReadBytes()}来丢弃已读的字节来回收该空间
 *
 * <pre>
 *  BEFORE discardReadBytes()
 *
 *      +-------------------+------------------+------------------+
 *      | discardable bytes |  readable bytes  |  writable bytes  |
 *      +-------------------+------------------+------------------+
 *      |                   |                  |                  |
 *      0      <=      readerIndex   <=   writerIndex    <=    capacity
 *
 *
 *  AFTER discardReadBytes()
 *
 *      +------------------+--------------------------------------+
 *      |  readable bytes  |    writable bytes (got more space)   |
 *      +------------------+--------------------------------------+
 *      |                  |                                      |
 * readerIndex (0) <= writerIndex (decreased)        <=        capacity
 *
 *
 * <h4>清空buffer索引</h4>
 * 可以通过调用{@link ByteBuf#clear()}方法来将readerIndex和writerIndex的值设为0.该方法不会将buffer中的内容设为0而是重设了两个
 * 指针。
 *
 * <pre>
 *  BEFORE clear()
 *
 *      +-------------------+------------------+------------------+
 *      | discardable bytes |  readable bytes  |  writable bytes  |
 *      +-------------------+------------------+------------------+
 *      |                   |                  |                  |
 *      0      <=      readerIndex   <=   writerIndex    <=    capacity
 *
 *
 *  AFTER clear()
 *
 *      +---------------------------------------------------------+
 *      |             writable bytes (got more space)             |
 *      +---------------------------------------------------------+
 *      |                                                         |
 *      0 = readerIndex = writerIndex            <=            capacity
 * </pre>
 *
 *
 * <h3>标记和重设</h3>
 * 每个buffer中有两个标记的索引，一个是存储readerIndex的一个是存储writerIndex的，我们总是可以通过调用一个reset方法来重新定位两个索引
 *
 * <h3>衍生的buffer</h3>
 *
 * 你可以通过以下方法来创建一个已存在的buffer的视图
 * You can create a view of an existing buffer by calling one of the following methods:
 * <ul>
 *   <li>{@link ByteBuf#duplicate()}</li>
 *   返回一个共享byte数组的byteBuf，但两个buffer的索引是独立的，该方法等同于{@code buf.slice(0, buf.capacity())}
 *   注意，由于是共享一个byte数组，所以调用该方法时不用调用{@link ByteBuf#retain()}方法来将引用计数增加1.
 *   <li>{@link ByteBuf#slice()}</li>
 *   等同于{@code buf.slice(buf.readerIndex(), buf.readableBytes())}，其他描述和duplicate()一样
 *   <li>{@link ByteBuf#slice(int, int)}</li>
 *   <li>{@link ByteBuf#readSlice(int)}</li>
 *   <li>{@link ByteBuf#retainedDuplicate()}</li>
 *   <li>{@link ByteBuf#retainedSlice()}</li>
 *   <li>{@link ByteBuf#retainedSlice(int, int)}</li>
 *   <li>{@link ByteBuf#readRetainedSlice(int)}</li>
 * </ul>
 * 一个衍生的buffer会拥有独立的readerIndex和writerIndex和标记索引，但它会共享同一个内部数据表示，这一点和NIO buffer一样
 * <p>
 *  如果需要一个buffer的深拷贝，请用{@link ByteBuf#copy()}方法
 *
 * <h4>Non-retained and retained derived buffers</h4>
 *
 * 注意{@link ByteBuf#duplicate()},{@link ByteBuf#slice()},{@link ByteBuf#slice(int, int)}和
 * {@link ByteBuf#readSlice(int)}不会调用返回的衍生buffer的{@link ByteBuf#retain()}方法，所以它的引用计数不会增加，如果
 * 你需要创建一个有增加过引用计数的衍生buffer，请使用{@link ByteBuf#retainedDuplicate()},{@link ByteBuf#retainedSlice()} ,
 * {@link ByteBuf#retainedSlice(int, int)}
 *
 * <h3>向JDK类型转换</h3>
 *
 * <h4>字节数组</h4>
 *
 * 如果一个{@link ByteBuf}是由一个字节数组作为内部支撑（区别于directBuffer），那么可以通过{@link ByteBuf#array()}来直接获得该数组
 * 。我们可以通过调用{@link ByteBuf#hasArray()}来判断该buffer内部是否有支撑数组
 *
 * <h4>NIO Buffers</h4>
 *
 * If a {@link ByteBuf} can be converted into an NIO {@link ByteBuffer} which shares its
 * content (i.e. view buffer), you can get it via the {@link ByteBuf#nioBuffer()} method.  To determine
 * if a buffer can be converted into an NIO buffer, use {@link ByteBuf#nioBufferCount()}.
 *
 * <h4>转换成字符串</h4>
 * 使用{@link ByteBuf#toString(Charset)}方法将一个{@link ByteBuf}转换成{@link String}，注意：{@link #toString()}不是个
 * 转换方法
 */
public class DocExplain {
    public static void main(String[] args){
        ByteBuf buf = Unpooled.copiedBuffer("hello,worldxixi",Charset.forName("utf-8"));
        //判断是堆上的数组还是堆外的数组
        if(buf.hasArray()){
            byte[] array = buf.array();
            System.out.println(new String(array,Charset.forName("utf-8")));
            //capacity会比写入的数据length要大，一般为3倍
            System.out.println(buf);

            for (int i = 0; i < buf.readableBytes(); i++) {
                System.out.println((char)buf.getByte(i));
            }
        }

        buf.writeCharSequence("hhh三生三世hhh三生三世hhh三生三世",Charset.forName("utf-8"));
        //capacity会自动扩容，一直到Integer.maxValue为止
        //128
        System.out.println(buf.capacity());
        //0
        System.out.println(buf.readerIndex());
        //60
        System.out.println(buf.writerIndex());
        //0,支撑数组的数组偏移量
        System.out.println(buf.arrayOffset());
        //60,{@code (this.writerIndex - this.readerIndex)}
        System.out.println(buf.readableBytes());

        System.out.println(buf.getCharSequence(18,6, Charset.forName("utf-8")));



    }
}
