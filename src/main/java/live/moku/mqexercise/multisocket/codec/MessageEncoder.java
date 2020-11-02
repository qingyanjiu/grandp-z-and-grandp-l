package live.moku.mqexercise.multisocket.codec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import live.moku.mqexercise.Message;
import live.moku.mqexercise.Serializer;


public class MessageEncoder extends MessageToByteEncoder<MessageStruct> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MessageStruct message, ByteBuf out) throws Exception {
        //长度
        out.writeInt(message.getLength());
        //消息序列号
        out.writeLong(message.getRequestId());
        //消息主体
        out.writeBytes(message.getBody());
    }
}
