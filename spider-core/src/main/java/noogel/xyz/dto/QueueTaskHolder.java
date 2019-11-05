package noogel.xyz.dto;

public class QueueTaskHolder {
    private final QueueTask queueTask;
    private QueueSerializer queueSerializer = new DefaultQueueSerializer();

    public QueueTaskHolder(QueueTask queueTask) {
        this.queueTask = queueTask;
    }

    public QueueTaskHolder(byte[] queueTaskBytes) {
        this.queueTask = queueSerializer.deserialize(queueTaskBytes);
    }

    public <T extends QueueTask> T getQueueTask() {
        return (T) queueTask;
    }

    public byte[] getBytesTask() {
        return queueSerializer.serialize(queueTask);
    }

}
