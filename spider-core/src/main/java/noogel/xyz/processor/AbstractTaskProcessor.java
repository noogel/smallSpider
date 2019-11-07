package noogel.xyz.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AbstractTaskProcessor implements TaskProcessor {
    private static final int cpuProcessorNum = Runtime.getRuntime().availableProcessors();
    private static ExecutorService executors = Executors.newFixedThreadPool(cpuProcessorNum);
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(String... args) throws InterruptedException {
        singleTask(args);
    }

    private void multiTask(String... args) {
        List<CompletableFuture<Boolean>> tasks = new ArrayList<>();
        for (int i = 0; i < cpuProcessorNum; i++) {
            tasks.add(
                    CompletableFuture.supplyAsync(() -> singleTask(args), executors)
            );
        }
        CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0])).join();
    }

    private Boolean singleTask(String... args) {
        try {
            process();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return true;
    }
}
