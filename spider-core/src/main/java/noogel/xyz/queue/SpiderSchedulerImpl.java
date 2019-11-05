package noogel.xyz.queue;

import noogel.xyz.dto.QueueTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class SpiderSchedulerImpl implements SpiderScheduler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QueueFactory queueFactory;


    @Override
    public <T extends QueueTask> void addTask(T t) {
        // 1. 任务去重
        // 2. 优先级管理
        // 3. 限速控制
        queueFactory.getQueue().add(t);
    }

    @Override
    public <T extends QueueTask> boolean consumeTask(Consumer<T> consumer) {
        queueFactory.getQueue().consume(consumer);
        return true;
    }
}
