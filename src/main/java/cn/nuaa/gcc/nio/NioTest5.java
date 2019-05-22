package cn.nuaa.gcc.nio;

import java.nio.ByteBuffer;

/**
 * {@author: gcc}
 * {@Date: 2019/3/18 09:51}
 * {@link ByteBuffer#slice()}slicebuffer和原有的buffer共享相同的底层数组（但数组指针是独立的，capacity，position等等。。）
 * 创建一个内容共享的ByteBuffer
 * 特点：1.设置原buffer的position为2，limit为6，则调用slice方法后新的bufferposition为0，limit = capacity = 4，内容和原
 * buffer中2-6的元素对应。
 * 2.对新buffer的元素作出改变则原buffer的对应元素也会作出改变,反之亦然。
 */
public class NioTest5 {
    public static void main(String[] args) throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }
        buffer.position(2);
        buffer.limit(6);

        ByteBuffer slice = buffer.slice();
        System.out.println(slice.capacity());
        System.out.println(slice.limit());
        System.out.println(slice.position());
        System.out.println("=========");

        for (int i = 0; i < slice.capacity(); i++) {
            byte b = slice.get();
            b *= 2;
            slice.put(i, b);
        }

        buffer.clear();
        while (buffer.hasRemaining()){
            System.out.println(buffer.get());
        }

    }
}
