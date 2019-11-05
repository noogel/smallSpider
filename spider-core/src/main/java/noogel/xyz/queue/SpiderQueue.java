package noogel.xyz.queue;

import noogel.xyz.config.SpiderQueueConfig;
import noogel.xyz.dto.QueueTask;

import java.util.function.Consumer;

/**
 * 任务队列
 */
public interface SpiderQueue {

    default void setQueueConfig(SpiderQueueConfig config) {
    }

    <T extends QueueTask> boolean add(T t);

    <T extends QueueTask> void consume(Consumer<T> consumer);

    long size();
}
