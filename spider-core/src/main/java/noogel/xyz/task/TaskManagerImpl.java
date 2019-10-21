package noogel.xyz.task;

import noogel.xyz.model.TaskModel;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class TaskManagerImpl implements TaskManager {
    private static ConcurrentLinkedQueue<TaskModel> concurrentLinkedQueue = new ConcurrentLinkedQueue<TaskModel>();

    public boolean add(TaskModel taskModel) {
        return concurrentLinkedQueue.offer(taskModel);
    }

    public TaskModel pop() {
        return concurrentLinkedQueue.poll();
    }

    @Override
    public long size() {
        return concurrentLinkedQueue.size();
    }

}
