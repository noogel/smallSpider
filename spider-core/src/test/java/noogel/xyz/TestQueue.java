package noogel.xyz;

import junit.framework.TestCase;
import noogel.xyz.config.TestConfiguration;
import noogel.xyz.model.TaskModel;
import noogel.xyz.task.TaskManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@TestConfiguration
public class TestQueue {

    private List<Integer> ddd = new ArrayList<>();

    @Autowired
    private TaskManager taskManager;

    private boolean addToArrayList(int ii) {
        synchronized (ddd) {
            return ddd.add(ii);
        }
    }

    @Test
    public void testConcurrentQueue() {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        List<CompletableFuture<Boolean>> futureList = new ArrayList<>();
        for (int i = 0; i < 1_000_000; i++) {
            int finalI = i;
            futureList.add(CompletableFuture.supplyAsync(() -> taskManager.add(TaskModel.of(finalI + "")), threadPool));
        }
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0])).join();
        TestCase.assertEquals(taskManager.size(), 1_000_000);
    }

    @Test
    public void testConcurrentQueue2() {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        List<CompletableFuture<Boolean>> futureList = new ArrayList<>();
        for (int i = 0; i < 1_000_000; i++) {
            int finalI = i;
            futureList.add(CompletableFuture.supplyAsync(() -> addToArrayList(finalI), threadPool));
        }
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0])).join();
        TestCase.assertEquals(ddd.size(), 1_000_000);
    }
}
