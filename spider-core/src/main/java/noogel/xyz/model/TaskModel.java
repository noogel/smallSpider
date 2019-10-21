package noogel.xyz.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import noogel.xyz.config.RequestHeaderEnum;

import java.util.List;

@Data
@NoArgsConstructor
public class TaskModel {

    private String url;
    private RequestHeaderEnum headerEnum;
    private List<String> exps;

    public static TaskModel of(String url) {
        TaskModel model = new TaskModel();
        model.setUrl(url);
        return model;
    }
}
