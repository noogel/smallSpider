package noogel.xyz.zk;

import noogel.xyz.model.TaskModel;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.queue.QueueConsumer;
import org.apache.curator.framework.state.ConnectionState;

public class ZKQueueConsumer implements QueueConsumer<TaskModel> {


    @Override
    public void consumeMessage(TaskModel taskModel) throws Exception {
        System.out.println(taskModel.getUrl());
        // TODO 如果处理程序抛出异常，并不能再次进入队列被其它消费者消费。
        throw new NullPointerException();
    }

    @Override
    public void stateChanged(CuratorFramework client, ConnectionState newState) {

    }
}
