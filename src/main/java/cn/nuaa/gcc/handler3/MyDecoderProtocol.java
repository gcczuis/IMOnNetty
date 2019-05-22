package cn.nuaa.gcc.handler3;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * {@author: gcc}
 * {@Date: 2019/4/1 10:22}
 *
 * 在编写前可以先看下{@link ReplayingDecoder}的doc说明
 *
 * {@link ReplayingDecoder}的泛型指的是state的类型，如果不需要使用{@link ReplayingDecoder#checkpoint(Object)}的话，则可以
 * 指定一个{@link Void}
 *
 */
public class MyDecoderProtocol extends ReplayingDecoder<State> {

    //初始化state，如果不初始化，在 switch (state())会报空指针异常
    public MyDecoderProtocol() {
        super(State.read_length);
    }

    //保存长度的状态，配合checkpoint()
    private int length;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyDecoderProtocol invoked");

        MyProtocol myProtocol = new MyProtocol();
        //记录现在的readerIndex，因为这个解码器继承的是ReplayingDecoder，如果在读取内容的时候不够字节，内部实现会抛出一个error
        //在捕获这个error的代码块中会调用in.readerIndex(checkpoint);将读索引重置成checkpoint，然后如果有新的数据来到，则重新从
        //checkpoint索引处读取数据。

        //我这里用 checkpoint()记录下当前的
        switch (state()){
            case read_length:
                length = in.readInt();
                checkpoint(State.read_content);
                //这里不需要break，因为很有可能下面还有数据，可以将content一起读出来
            case read_content:
                byte[] content = new byte[length];
                in.readBytes(content);
                myProtocol.setLength(this.length);
                myProtocol.setContent(content);
                out.add(myProtocol);
                checkpoint(State.read_length);
                break;
            default:
                throw new Error("Shouldn't reach here.");

        }
    }
}

enum State{
    read_length,
    read_content
}


