package noogel.xyz.task;

import noogel.xyz.model.TaskModel;

public interface TaskManager {

    boolean add(TaskModel taskModel);

    TaskModel pop();

    long size();

}
