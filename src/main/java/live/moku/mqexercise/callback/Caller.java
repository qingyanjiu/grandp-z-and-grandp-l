package live.moku.mqexercise.callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class Caller {

    @Autowired
    private Processor processor;

    public void calculate() {

        CompletableFuture cf = CompletableFuture.runAsync(() -> {
            this.processor.process(l -> System.out.println("耗时 " + l + " ms"));
        });

    }
}
