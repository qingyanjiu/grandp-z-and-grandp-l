package live.moku.mqexercise.multisocket.netty;

import java.util.concurrent.atomic.AtomicInteger;

public class Calculator {

    private AtomicInteger count = new AtomicInteger(0);

    private long start = System.currentTimeMillis();

    public void add() {
        this.count.getAndIncrement();
        if(this.count.get() >= NettyClient.LOOP_TIMES)
            System.out.println("完成,耗时 " + (System.currentTimeMillis() -start));
    }

}
