package live.moku.mqexercise.multisocket.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import live.moku.mqexercise.Message;
import live.moku.mqexercise.multisocket.codec.MessageStruct;

public class ClientHandler extends SimpleChannelInboundHandler<MessageStruct> {

    private Calculator calculator;

    public ClientHandler() {
        this.calculator = new Calculator();
    }

    //处理服务端返回的数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageStruct msg) throws Exception {
        System.out.println("接受到server响应数据: " + msg.getMessage());
        this.calculator.add();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}

