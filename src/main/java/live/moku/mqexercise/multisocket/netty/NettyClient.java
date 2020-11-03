package live.moku.mqexercise.multisocket.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import live.moku.mqexercise.multisocket.codec.MessageDecoder;
import live.moku.mqexercise.multisocket.codec.MessageEncoder;
import live.moku.mqexercise.multisocket.codec.MessageStruct;
import org.springframework.stereotype.Service;

@Service
public class NettyClient {

    public static final int LOOP_TIMES = 100_000;

    private String host = "127.0.0.1";
    private int port = 8090;
    private Channel channel;

    //连接服务端的端口号地址和端口号
    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public NettyClient() {
    }

    public void start() throws Exception {
        final EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class)  // 使用NioSocketChannel来作为连接用的channel类
                .handler(new ChannelInitializer<SocketChannel>() { // 绑定连接初始化器
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        System.out.println("正在连接中...");
                        ChannelPipeline pipeline = ch.pipeline();
                        //编码request
                        pipeline.addLast(new MessageEncoder());
                        //解码response
                        pipeline.addLast(new MessageDecoder(1024 * 1024, 0, 4));
                        //客户端处理类
                        pipeline.addLast(new ClientHandler());

                    }
                });
        //发起异步连接请求，绑定连接端口和host信息
        final ChannelFuture future = b.connect(host, port).sync();

        future.addListener(new ChannelFutureListener() {

            @Override
            public void operationComplete(ChannelFuture arg0) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("连接服务器成功");

                } else {
                    System.out.println("连接服务器失败");
                    future.cause().printStackTrace();
                    group.shutdownGracefully(); //关闭线程组
                }
            }
        });

        this.channel = future.channel();
    }

    public Channel getChannel() {
        return channel;
    }

    public static void main(String[] args) throws Exception {
        NettyClient nettyClient = new NettyClient();
        nettyClient.start();
        Channel channel = nettyClient.getChannel();
        for (int i = 0; i < LOOP_TIMES; i++) {
            //消息体
            MessageStruct message = new MessageStruct("客户端说话了: " + i);
            //channel对象可保存在map中，供其它地方发送消息
            channel.writeAndFlush(message);
        }
    }
}