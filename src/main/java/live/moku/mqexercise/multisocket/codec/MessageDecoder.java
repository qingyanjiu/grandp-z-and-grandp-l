package live.moku.mqexercise.multisocket.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class MessageDecoder extends LengthFieldBasedFrameDecoder {

    public MessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf buf = null;
        MessageStruct message = null;
        try {
            //in 读取到byte buffer中
            buf = (ByteBuf) super.decode(ctx, in);
            if(buf == null) {
                return null;
            }
            //获取消息长度
            int length = buf.readInt();
            //获取消息序列号 8字节
            long requestId = buf.readLong();
            //定义 除去8字节序列号的消息体长度 的字节数组
            byte[] body = new byte[length - 8];
            //bytebuffer中读取字节数组
            buf.readBytes(body);
            //获取消息体，这里转成了message，复杂的还可以转
            String content = body.toString();
            message = new MessageStruct(requestId, content);
        } finally {
            if(buf != null) {
                buf.release();
            }
        }
        return message;
    }
}
