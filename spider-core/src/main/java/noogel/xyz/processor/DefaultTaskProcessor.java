package noogel.xyz.processor;

import noogel.xyz.dto.PageQueueTaskDto;
import noogel.xyz.dto.QueueTask;
import noogel.xyz.queue.SpiderScheduler;
import noogel.xyz.utils.OkHttpHelper;
import noogel.xyz.utils.XpathHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DefaultTaskProcessor extends AbstractTaskProcessor {

    @Autowired
    private SpiderScheduler spiderScheduler;

    @Override
    public void process() throws InterruptedException {
        while (true) {
            boolean taskResult = spiderScheduler.consumeTask(queueTask -> subProcess(queueTask));
            if (!taskResult) {
                logger.warn("run failed, sleep 1s.");
                Thread.sleep(1000);
            }
        }
    }


    private boolean subProcess(QueueTask queueTask) {
        PageQueueTaskDto task = (PageQueueTaskDto) queueTask;
        logger.info("process url:" + task.getUrl());
        // 0. 前置校验
        if (queueTask == null) {
            return false;
        }
        if (queueTask.getUrl().endsWith(".apk")) {
            return true;
        }
        // 1. 获取任务
        // 2. 解析任务
        // 3. 获取数据
        String responseHtml = OkHttpHelper.sendRequest(task.getUrl());
        // 4. 解析数据
        Map<String, List<String>> listMap = XpathHelper.parseContent(responseHtml, task.getExps().toArray(new String[0]));
        // 5. 存储数据
        // 添加到新的任务
        List<String> subUrls = patchSubUrls(
                task.getUrl(),
                listMap.entrySet().stream()
                        .map(Map.Entry::getValue)
                        .flatMap(Collection::stream)
                        .distinct()
                        .collect(Collectors.toList())
        );
        subUrls.forEach(subUrl -> {
            PageQueueTaskDto pageQueueTaskDto = PageQueueTaskDto.of(subUrl, task.getExps());
            spiderScheduler.addTask(pageQueueTaskDto);
        });

        return true;
    }

    private List<String> patchSubUrls(String url, List<String> subPath) {
        try {
            URL parseUrl = new URL(url);
            String urlHost = String.format("%s://%s", parseUrl.getProtocol(), parseUrl.getHost());
            return subPath.stream().filter(sub -> sub.startsWith("/") || sub.startsWith("http"))
                    .map(sub -> sub.startsWith("http") ? sub : urlHost + sub)
                    .distinct()
                    .collect(Collectors.toList());
        } catch (MalformedURLException e) {
            logger.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }
}
