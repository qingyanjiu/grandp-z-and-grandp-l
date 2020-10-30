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

    public Message listen(byte[] buffer, InputStream inputStream) {
        int l = 0;
        Message ret = null;
        try {
            byte[] b = new byte[4];
            inputStream.read(b, 0, 4);
            String lengthStr = new String(b);
            int length = new BigInteger(lengthStr, 16).intValue();
            while ((l = inputStream.read(buffer, 0, length)) != -1) {
                ret = Serializer.deserialize(buffer);

                System.out.println("收到李大爷的响应 " + ret.toString());

                inputStream.read(b, 0, 4);
                lengthStr = new String(b);
                length = new BigInteger(lengthStr, 16).intValue();
            }
        } catch (IOException e) {
            System.out.println("IOEXCEPTION");
        }
        return ret;
    }
}
