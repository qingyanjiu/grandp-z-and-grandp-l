package live.moku.mqexercise.multisocket.codec;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 自定义消息格式
 */

@Getter
public class MessageStruct implements Serializable, Cloneable {

    private static final long serialVersionUID = 7670741182621285695L;

    //消息长度
    private int length;
    //消息内容
    @Setter
    private String message;

    private byte[] body;

    private long requestId;

    private static final AtomicLong flowNum = new AtomicLong(0);

    private transient static long startTimeStamp ;

    public MessageStruct(String msg) {
        this.requestId = flowNum.incrementAndGet();
        this.message = msg;
        this.body = msg.getBytes(Charset.forName("UTF-8"));
        //总消息长度为requestId字段长度(long类型 8字节) +body消息主体长度的总和
        //length字段表示 requestId + body的总长度，所以总长度减去 requestId的8个字节，就是body长度
        this.length = body.length + 8;
    }

    public MessageStruct(long requestId, String msg) {
        this.requestId = requestId;
        this.message = msg;
        this.body = msg.getBytes(Charset.forName("UTF-8"));
        this.length = body.length + 8;
    }

    public int getLength() {
        return length;
    }

    public void setStartTimeStamp(long ts) {
        startTimeStamp=ts;
    }

    public long getStartTimeStamp() {
        return startTimeStamp;
    }

    public String getMessage() {
        return this.message;
    }

    public long getRequestId() {
        return this.requestId;
    }

    public byte[] getBody() {
        return this.body;
    }

    @Override
    public String toString() {
        return String.format("echo:[message=%s,requestId=%d]", message, requestId);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }


}
