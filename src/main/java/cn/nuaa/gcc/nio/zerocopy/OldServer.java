package cn.nuaa.gcc.nio.zerocopy;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * {@author: gcc}
 * {@Date: 2019/3/21 14:55}
 *
 *
 */
public class OldServer {
    public static void main(String[] args)throws Exception{
        ServerSocket serverSocket = new ServerSocket(8899);
        while(true){
            Socket socket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            byte[] bytes = new byte[4096];
            while (true){
                int readCount = dataInputStream.read(bytes);
                if(readCount==0){
                    break;
                }
            }
        }
    }
}
