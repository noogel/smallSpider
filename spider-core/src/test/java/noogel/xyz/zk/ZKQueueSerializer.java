package noogel.xyz.zk;

import org.apache.curator.framework.recipes.queue.QueueSerializer;

import java.io.*;

public class ZKQueueSerializer implements QueueSerializer {
    @Override
    public byte[] serialize(Object item) {
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
    public Object deserialize(byte[] bytes) {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream sIn;
        try {
            sIn = new ObjectInputStream(in);
            return sIn.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
