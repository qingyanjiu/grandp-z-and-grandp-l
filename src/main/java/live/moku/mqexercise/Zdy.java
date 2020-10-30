package live.moku.mqexercise;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;

@Component
public class Zdy {

    public void say(OutputStream outputStream, Message message) {
        try {
            System.out.println("张大爷说话了" + message);
            outputStream.write(Serializer.serialize(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen(byte[] buffer, InputStream inputStream) {
        int l = 0;
        Message ret = null;
        try {
            byte[] b = new byte[4];
            inputStream.read(b, 0, 4);
            String lengthStr = new String(b);
            int length = new BigInteger(lengthStr, 16).intValue();

            int times = 0;
            long start = System.currentTimeMillis();

            while ((l = inputStream.read(buffer, 0, length)) != -1) {
                ret = Serializer.deserialize(buffer);

                System.out.println("收到李大爷的响应 " + ret.toString());

                //获取一次+1，当收到响应次数达到发送条数的时候，退出，说明已经完成所有的交互
                //也就是说 张大爷发了10条，李大爷回了10条，张大爷全都接收到了，任务完成。计算时间
                times++;
                if (times >= Client.POOL_TIMES) {
                    break;
                } else {
                    inputStream.read(b, 0, 4);
                    lengthStr = new String(b);
                    length = new BigInteger(lengthStr, 16).intValue();
                }
            }

            System.out.println("总耗时 " + (System.currentTimeMillis() - start) + "ms");

        } catch (IOException e) {
            System.out.println("张大爷 IOEXCEPTION");
            throw new RuntimeException(e);
        }
    }
}
