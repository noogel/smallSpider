package noogel.xyz.queue;

import noogel.xyz.dto.QueueTask;
import noogel.xyz.dto.QueueTaskHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

public class SpiderQueueConcurrentQueue implements SpiderQueue {
    private static ConcurrentLinkedQueue<QueueTask> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
    private static ConcurrentMap<String, Boolean> concurrentMap = new ConcurrentHashMap<>();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public <T extends QueueTask> boolean add(T t) {
        QueueTaskHolder holder = new QueueTaskHolder(t);
        if (concurrentMap.containsKey(t.getUrl())) {
            return true;
        }
        concurrentMap.put(t.getUrl(), true);
        return concurrentLinkedQueue.offer(t);

    }

    @Override
    public <T extends QueueTask> void consume(Consumer<T> consumer) {
        QueueTask queueTask = concurrentLinkedQueue.poll();
        if (null == queueTask) {
            return;
        }
        concurrentMap.remove(queueTask.getUrl());
        consumer.accept((T) queueTask);
    }

    @Override
    public long size() {
        return concurrentLinkedQueue.size();
    }
}
