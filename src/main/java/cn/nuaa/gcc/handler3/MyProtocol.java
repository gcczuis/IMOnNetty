package cn.nuaa.gcc.handler3;

/**
 * {@author: gcc}
 * {@Date: 2019/4/1 10:20}
 * 自定义协议的封装对象
 *
 */
public class MyProtocol {
    private int length;
    private byte[] content;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
