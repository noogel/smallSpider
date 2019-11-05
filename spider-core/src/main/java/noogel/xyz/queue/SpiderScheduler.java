package noogel.xyz.queue;

import noogel.xyz.dto.QueueTask;

import java.util.function.Consumer;

/**
 * 任务调度
 */
public interface SpiderScheduler {

    <T extends QueueTask> void addTask(T t);

    <T extends QueueTask> boolean consumeTask(Consumer<T> consumer);

}
