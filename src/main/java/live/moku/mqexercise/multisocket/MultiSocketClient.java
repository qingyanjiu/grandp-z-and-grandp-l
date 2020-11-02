package live.moku.mqexercise.multisocket;

import live.moku.mqexercise.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiSocketClient {

    public static final int POOL_TIMES = 10000;

    public static void main(String[] args) throws IOException, InterruptedException {
        MultiSocketZdy zdy = new MultiSocketZdy();
        Message message = new Message();
        byte[] b = new byte[100];

//        CompletableFuture[] cfs = new CompletableFuture[POOL_TIMES];

//        ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < POOL_TIMES ; i++) {
            final int _i = i;
//            cfs[_i] = CompletableFuture.runAsync(() -> {
                try (Socket socket = new Socket("localhost", 8088);
                     OutputStream os = socket.getOutputStream();
                     InputStream is = socket.getInputStream();) {
                    message.setContent("zdy said " + _i);
                    message.setUuid(_i);
                    zdy.say(os, message);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
//            }, es);
        }

//        CompletableFuture.allOf(cfs).join();
        System.out.println("总耗时 " + (System.currentTimeMillis() - startTime) + " ms");

//        es.shutdown();

    }
}