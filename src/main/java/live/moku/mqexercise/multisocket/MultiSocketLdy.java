package live.moku.mqexercise.multisocket;

import live.moku.mqexercise.Message;
import live.moku.mqexercise.Serializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;

@Component
public class MultiSocketLdy {

    public void say(OutputStream outputStream, Message message) {
        try {
            outputStream.write(Serializer.serialize(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen(byte[] buffer, InputStream inputStream, OutputStream outputStream) {
        int l = 0;
        Message ret = null;
        Message reply = new Message();
        int number = 0;
        String content = "";
        try {
            //先拿前四个字节，这个是以16进制表示的一条消息的长度
            byte[] b = new byte[4];
            inputStream.read(b, 0, 4);
            //byte数组转成获取字符串
            String lengthStr = new String(b);
            //字符串表示的16进制数字转成10进制int
            int length = new BigInteger(lengthStr, 16).intValue();
            //从后面开始读取流信息
            while ((l = inputStream.read(buffer, 0, length)) != -1) {
                //获取当前第一个对象，进行反序列化
                ret = Serializer.deserialize(buffer);

                System.out.println("收到张大爷的消息 " + ret.toString());

                number = ret.getUuid();
                content = "zdy reply " + number;
                reply.setContent(content);
                reply.setUuid(number);
                this.say(outputStream, reply);

                //继续读取下一个对象的长度
                if(inputStream.read(b, 0, 4) != -1) {
                    lengthStr = new String(b);
                    //进入下一个循环，取第二个对象。。。
                    length = new BigInteger(lengthStr, 16).intValue();
                } else {
                    //如果没有下一个对象了，说明客户端发送完了，退出
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println("李大爷 IOEXCEPTION");
            throw new RuntimeException(e);
        }
    }
}
