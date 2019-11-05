package noogel.xyz.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import noogel.xyz.config.RequestHeaderEnum;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@NoArgsConstructor
public class TaskModel implements Serializable {

    private static final long serialVersionUID = -5809782578272943999L;

    private String url;
    private RequestHeaderEnum headerEnum;
    private List<String> exps;

    public static TaskModel of(String url, String... exps) {
        TaskModel model = new TaskModel();
        model.setUrl(url);
        model.setExps(Stream.of(exps).collect(Collectors.toList()));
        return model;
    }

    public static TaskModel of(byte[] taskBytes) {
        ByteArrayInputStream in = new ByteArrayInputStream(taskBytes);
        ObjectInputStream sIn;
        try {
            sIn = new ObjectInputStream(in);
            return (TaskModel) sIn.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] getBytes() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream sOut;
        try {
            sOut = new ObjectOutputStream(out);
            sOut.writeObject(this);
            sOut.flush();
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
