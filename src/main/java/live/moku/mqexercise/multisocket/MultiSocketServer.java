package live.moku.mqexercise.multisocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiSocketServer {

    public static void main(String[] args) throws IOException {

        ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        final ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(8088));
        MultiSocketLdy ldy = new MultiSocketLdy();
        byte[] b = new byte[100];
        while (true) {
            Socket socket = serverSocket.accept();
            es.execute(() -> {
                try (OutputStream os = socket.getOutputStream();
                     InputStream is = socket.getInputStream();) {
                    ldy.listen(b, is, os);
                } catch (Exception e) {
                    System.out.println("客户端连接已断开 " + e.getCause());
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
