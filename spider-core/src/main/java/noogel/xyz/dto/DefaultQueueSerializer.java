package noogel.xyz.dto;

import java.io.*;


public class DefaultQueueSerializer implements QueueSerializer {

    @Override
    public <T extends QueueTask> byte[] serialize(T item) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream sOut;
        try {
            sOut = new ObjectOutputStream(out);
            sOut.writeObject(item);
            sOut.flush();
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T extends QueueTask> T deserialize(byte[] bytes) {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream sIn;
        try {
            sIn = new ObjectInputStream(in);
            return (T) sIn.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
