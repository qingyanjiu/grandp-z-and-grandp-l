package live.moku.mqexercise.callback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class Caller {

    @Autowired
    private Processor processor;

    public void calculate() {

        CompletableFuture cf = CompletableFuture.runAsync(() -> {
            this.processor.process(l -> log.info("耗时 " + l + " ms"));
        });

    }
}
