package cn.nuaa.gcc.nio;

import java.nio.Buffer;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.security.SecureRandom;

/**
 * {@author: gcc}
 * {@Date: 2019/3/18 09:51}
 * capacity:是buffer的容量，永远不会被改变，capacity作为一个索引值（buffer底层是数组）也永远不会被达到也永远非负
 * limit：limit初始化时总是和capacity一致，limit索引上的值以及之后的值是不可读也不可写的，limit值非负且小于等于capacity
 * position：position值是下一个要去读或者要去写元素的索引值，position值非负且小于等于limit
 * <p>
 * buffer操作有两种类型：
 * 1.相对操作：相对操作在当前position位置读或者写一个或者n个元素，与之同时也会将position向前1个或者n个位置（这是put或者get操作自动完成的）
 * 如果这种相对put操作超过了limit则会报{@link java.nio.BufferOverflowException},如果这种相对get操作也超过了limit则会报
 * {@link BufferUnderflowException}异常，上述两种情况中都不会传递数据。
 * 2.绝对操作：绝对操作{@link ByteBuffer#get(int)}和{@link ByteBuffer#put(int, byte)}这种显示指定索引的操作
 * 不会影响position值，但索引值如果超过limit会报{@link IndexOutOfBoundsException}异常
 * <p>
 * 规则：0<=mark<=position<=limit<=capacity
 * <p>
 * apiNote:
 * {@link Buffer#clear()}clear方法并不是真正的将buffer内部数据清空+各个数组指针回归初始值，而只是将limit设为capacity，position设为0
 * 这样通过put操作，可以将原来的值覆盖掉，来实现一种clear的效果。（是为了供读取channel数据以及相对的put操作所设）
 * <p>
 * {@link Buffer#flip()}flip方法将limit设为position的值，并将position的值设为0.（是为了将buffer数据写入到channel数据以及相对的get操作所设）
 * <p>
 * {@link Buffer#rewind()}rewind方法不改变limit的值，将position的值设为0。该方法为了重新读取数据而设立的。
 * <p>
 * {@link DirectByteBuffer#compact()}是一个压缩方法，实现的具体内容为：
 * 1. 将所有未读的数据复制到buffer起始位置处。
 * 2. 将position设为最后一个未读元素的后面
 * 3. 将limit设为capacity。
 * 4. 丢弃mark，设为-1
 * 5. 现在buffer就准备好了，但是不会覆盖未读的数据。
 */
public class NioTest1 {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        System.out.println("capacity:" + buffer.capacity());
        for (int i = 0; i < 9; i++) {
            int randomNum = new SecureRandom().nextInt(20);
            buffer.put((byte) randomNum);
        }
        System.out.println("before flip limit:" + buffer.limit());
        buffer.flip();
        System.out.println("after flip limit:" + buffer.limit());
        System.out.println("enter while loop");
        while (buffer.hasRemaining()) {
            System.out.println("position:" + buffer.position());
            System.out.println("limit:" + buffer.limit());
            System.out.println("capacity:" + buffer.capacity());
            System.out.println(buffer.get());
        }

    }
}
