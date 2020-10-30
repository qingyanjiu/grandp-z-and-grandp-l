package live.moku.mqexercise;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException, InterruptedException {
        Zdy zdy = new Zdy();
        Message message = new Message();
        byte[] b = new byte[100];
        long start = System.currentTimeMillis();
        try (Socket socket = new Socket("localhost", 8088);
             OutputStream os = socket.getOutputStream();
             InputStream is = socket.getInputStream();) {
            for (int i = 0; i < 10000; i++) {
                message.setContent("zdy said " + i);
                message.setUuid(i);
                zdy.say(os, message);
            }

            zdy.listen(b, is);
        } finally {
            System.out.println("总耗时 " + (System.currentTimeMillis() - start) / 1000);
        }
    }
}