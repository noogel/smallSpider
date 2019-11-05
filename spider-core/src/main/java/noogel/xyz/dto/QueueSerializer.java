package noogel.xyz.dto;

public interface QueueSerializer {

    <T extends QueueTask> byte[] serialize(T item);

    <T extends QueueTask> T deserialize(byte[] bytes);
}
