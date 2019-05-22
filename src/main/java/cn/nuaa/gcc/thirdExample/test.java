
import java.io.*;
import java.net.*;
class TextClient
{
    public static void main(String[] args) throws Exception
    {
        Socket s = new Socket("172.168.0.169",10007);

        //DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        long t = System.currentTimeMillis();
        //dos.writeLong(t);

        BufferedReader bufr =
                new BufferedReader(new FileReader("IPDemo.java"));
        PrintWriter out = new PrintWriter(s.getOutputStream(),true);
        String line = null;
        out.println(String.valueOf(t));
        while ((line=bufr.readLine())!=null)
        {
            out.println(line);
        }
        out.println(String.valueOf(t));
        //dos.writeLong(t);
        BufferedReader bufIn =
                new BufferedReader(new InputStreamReader(s.getInputStream()));
        String str = bufIn.readLine();
        System.out.println(str);
        bufr.close();
        s.close();
    }
}
class TextServer
{
    public static void main(String[] args) throws Exception
    {
        ServerSocket ss = new ServerSocket(10007);
        Socket s = ss.accept();

        //DataInputStream dis = new DataInputStream(s.getInputStream());
        //Long t = dis.readLong();

        BufferedReader bufIn =
                new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter pw = new PrintWriter(new FileWriter("severCopy.txt"),true);
        String line = null;
        String head = bufIn.readLine();
        pw.println(line);
        while ((line=bufIn.readLine())!=null)
        {
            if(line==head)
                break;
            pw.println(line);
        }
        PrintWriter priOut = new PrintWriter(s.getOutputStream(),true);
        priOut.println("文件上传成功！");
        pw.close();
        s.close();
        ss.close();
    }
}
