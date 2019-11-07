package noogel.xyz.zk;

import noogel.xyz.config.TestConfiguration;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@TestConfiguration
public class TestZKLocking {

    private static final Logger logger = LoggerFactory.getLogger(TestZKLocking.class);
    private static final CuratorFramework client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181")
            .sessionTimeoutMs(1000)
            .connectionTimeoutMs(1000)
            .canBeReadOnly(false)
            .retryPolicy(new ExponentialBackoffRetry(1000, Integer.MAX_VALUE))
            .defaultData(null)
            .build();
    private static Integer availableProcessor = Runtime.getRuntime().availableProcessors();
    private static ExecutorService threadPool = Executors.newFixedThreadPool(availableProcessor);


    @Before
    public void init() {
        client.start();
    }

    private void doGetLocking() {
        while (true) {
            try {
                process();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private void process() throws Exception {
        InterProcessMutex ipm = new InterProcessMutex(client, "/zktest/distributed_lock");

        try {
            ipm.acquire();

            logger.info("Thread ID:" + Thread.currentThread().getId() + " acquire the lock");

            Thread.sleep(1000);

            logger.info("Thread ID:" + Thread.currentThread().getId() + " release the lock");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            ipm.release();
        }
    }

    @Test
    public void testConcurrentQueue() {
//        List<CompletableFuture<Void>> futureList = new ArrayList<>();
//        for (int i = 0; i < availableProcessor; i++) {
//            futureList.add(CompletableFuture.runAsync(() -> doGetLocking(), threadPool));
//        }
//        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0])).join();
    }

}
