package live.moku.mqexercise.multisocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiSocketServer {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(8088));
        MultiSocketLdy ldy = new MultiSocketLdy();
        Socket socket = null;
        byte[] b = new byte[100];
        while (true) {
            socket = serverSocket.accept();
            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();
            try {
                ldy.listen(b, is, os);
            } catch (Exception e) {
                System.out.println("客户端连接已断开 " + e.getCause());
            }
        }
    }
}
