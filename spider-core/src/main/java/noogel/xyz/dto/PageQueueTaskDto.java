package noogel.xyz.dto;

import lombok.Getter;
import noogel.xyz.config.RequestHeaderEnum;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class PageQueueTaskDto implements QueueTask, Serializable {

    private static final long serialVersionUID = -5809782578272943999L;

    private String url;
    private RequestHeaderEnum headerEnum = RequestHeaderEnum.PC_CHROME;
    private List<String> exps;

    public static PageQueueTaskDto of(String url, String... exps) {
        PageQueueTaskDto dto = new PageQueueTaskDto();
        dto.url = url;
        dto.exps = Stream.of(exps).collect(Collectors.toList());
        return dto;
    }

    public static PageQueueTaskDto of(String url, List<String> exps) {
        PageQueueTaskDto dto = new PageQueueTaskDto();
        dto.url = url;
        dto.exps = exps;
        return dto;
    }
}
