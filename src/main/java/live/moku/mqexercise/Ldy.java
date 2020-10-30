package live.moku.mqexercise;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;

@Component
public class Ldy {

    public void say(OutputStream outputStream, Message message) {
        try {
            outputStream.write(Serializer.serialize(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Message listen(byte[] buffer, InputStream inputStream, OutputStream outputStream) {
        int l = 0;
        Message ret = null;
        Message reply = new Message();
        int number = 0;
        String content = "";
        try {
            byte[] b = new byte[4];
            inputStream.read(b, 0, 4);
            String lengthStr = new String(b);
            int length = new BigInteger(lengthStr, 16).intValue();
            while ((l = inputStream.read(buffer, 0, length)) != -1) {
                ret = Serializer.deserialize(buffer);

                System.out.println("收到张大爷的消息 " + ret.toString());

                number = ret.getUuid();
                content = "zdy reply " + number;
                reply.setContent(content);
                reply.setUuid(number);
                this.say(outputStream, reply);

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
