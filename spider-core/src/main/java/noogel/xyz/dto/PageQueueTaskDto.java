package noogel.xyz.dto;

import lombok.Data;
import noogel.xyz.config.RequestHeaderEnum;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class PageQueueTaskDto implements QueueTask {
    private String url;
    private RequestHeaderEnum headerEnum = RequestHeaderEnum.PC_CHROME;
    private List<String> exps;

    public static PageQueueTaskDto of(String url, String... exps) {
        PageQueueTaskDto dto = new PageQueueTaskDto();
        dto.setUrl(url);
        dto.setExps(Stream.of(exps).collect(Collectors.toList()));
        return dto;
    }

    public static PageQueueTaskDto of(String url, List<String> exps) {
        PageQueueTaskDto dto = new PageQueueTaskDto();
        dto.setUrl(url);
        dto.setExps(exps);
        return dto;
    }
}
