package cn.nuaa.gcc.nio.zerocopy;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;

/**
 * {@author: gcc}
 * {@Date: 2019/3/21 14:58}
 * 测试：在不需要修改的文件发送到socket服务端的场景下，用传统的方式来进行测试
 * 条件：300+M的MP4文件
 * 结果：平均用时3166ms
 */
public class OldClient {
    public static void main(String[] args)throws Exception{
        Socket socket = new Socket("localhost", 8899);
        String filename = "C:\\Users\\user\\Desktop\\tess.mp4";
        InputStream inputStream = new FileInputStream(filename);

        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        byte[] buffer = new byte[4096];
        long readCount;
        long total = 0;

        long startTime = System.currentTimeMillis();
        while ((readCount = inputStream.read(buffer)) >= 0){
            total += readCount;
            dataOutputStream.write(buffer);
        }
        System.out.println("发送总字节数： "+total+"，耗时："+(System.currentTimeMillis()-startTime));

        dataOutputStream.close();
        inputStream.close();
        socket.close();
    }
}
