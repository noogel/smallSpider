package noogel.xyz.queue;

import noogel.xyz.dto.QueueTask;

import java.util.function.Consumer;

public class SpiderQueueZookeeper implements SpiderQueue {

    @Override
    public <T extends QueueTask> boolean add(T t) {
        return false;
    }

    @Override
    public <T extends QueueTask> void consume(Consumer<T> consumer) {

    }

    @Override
    public long size() {
        return 0;
    }
}
