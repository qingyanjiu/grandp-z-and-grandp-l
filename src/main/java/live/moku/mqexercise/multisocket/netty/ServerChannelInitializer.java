package live.moku.mqexercise.multisocket.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import live.moku.mqexercise.multisocket.codec.MessageDecoder;
import live.moku.mqexercise.multisocket.codec.MessageEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private ServerHandler webSocketHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
//        //HttpServerCodec: 针对http协议进行编解码
//        pipeline.addLast("httpServerCodec", new HttpServerCodec());
//        //ChunkedWriteHandler分块写处理，文件过大会将内存撑爆
//        pipeline.addLast("chunkedWriteHandler", new ChunkedWriteHandler());
//        /**
//         * 作用是将一个Http的消息组装成一个完成的HttpRequest或者HttpResponse，那么具体的是什么
//         * 取决于是请求还是响应, 该Handler必须放在HttpServerCodec后的后面
//         */
//        pipeline.addLast("httpObjectAggregator", new HttpObjectAggregator(8192));
//
//        //用于处理websocket, /ws为访问websocket时的uri
//        pipeline.addLast("webSocketServerProtocolHandler", new WebSocketServerProtocolHandler("/ws"));

        // 字符串解码 和 编码
        pipeline.addLast("decoder", new MessageDecoder(1024 * 1024, 0, 4));
        pipeline.addLast("encoder", new MessageEncoder());

        // 自己的逻辑Handler
        pipeline.addLast("handler", new ServerHandler());
    }
}
