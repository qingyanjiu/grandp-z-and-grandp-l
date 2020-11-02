package live.moku.mqexercise.callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class Processor {

    public void process(PCallback callback) {
        long randomLong = ThreadLocalRandom.current().nextLong(0,10000);
        try {
            System.out.println("开始处理");
            Thread.sleep(randomLong);
            callback.callback(randomLong);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
