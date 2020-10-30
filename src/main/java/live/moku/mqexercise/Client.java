package live.moku.mqexercise;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

    public static final int POOL_TIMES = 1000000;

    public static void main(String[] args) throws IOException, InterruptedException {
        Zdy zdy = new Zdy();
        Message message = new Message();
        byte[] b = new byte[100];

        try (Socket socket = new Socket("localhost", 8088);
             OutputStream os = socket.getOutputStream();
             InputStream is = socket.getInputStream();) {
            //发送缓冲区大小，如果数据特别多要调大一些，否则会卡住
            socket.setSendBufferSize(524288000);
            for (int i = 0; i < POOL_TIMES; i++) {
                message.setContent("zdy said " + i);
                message.setUuid(i);
                zdy.say(os, message);
            }

            zdy.listen(b, is);
        }
    }
}