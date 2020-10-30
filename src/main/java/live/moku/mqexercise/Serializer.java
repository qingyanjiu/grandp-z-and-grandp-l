package live.moku.mqexercise;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Serializer {

    /**
     * 前四个字段是数据长度
     * 后面内容转为json字符串
     * 自定义协议, 序列化的时候将数据长度放到内容前面
     */
    private static byte[] putMessageLengthToByteArray(byte[] b) {
        byte[] res = null;
        if (b != null) {
            int length = b.length;
            String lengthStr = Integer.toHexString(length);
            StringBuilder sb = new StringBuilder(lengthStr);
            int sub = 4 - sb.length() >= 0 ? 4 - sb.length() : 0;
            for (int i = 0; i < sub ; i++) {
                sb.insert(0 , "0");
            }
            res = new byte[length + 4];
            for (int i = 0; i < 4 ; i++) {
                res[i] = sb.toString().getBytes()[i];
            }
            for (int i = 4; i < length + 4; i++) {
                res[i] = b[i-4];
            }
        }
        return res;
    }

    static byte[] serialize(Message message) {
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] ret = null;
        try {
            ret = objectMapper.writeValueAsBytes(message);
            ret = putMessageLengthToByteArray(ret);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ret;
    }

    static Message deserialize(byte[] bytes) {
        ObjectMapper objectMapper = new ObjectMapper();
        Message ret = null;
        try {
            ret = objectMapper.readValue(bytes, Message.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
