package noogel.xyz.processor;

import noogel.xyz.model.TaskModel;
import noogel.xyz.task.TaskManager;
import noogel.xyz.utils.OkHttpHelper;
import noogel.xyz.utils.XpathHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public abstract class AbstractTaskProcessor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TaskManager taskManager;

    public void process() {
        // 1. 获取任务
        TaskModel task = taskManager.pop();
        // 2. 解析任务
        // 3. 获取数据
        String responseHtml = OkHttpHelper.sendRequest(task.getUrl());
        // 4. 解析数据
        Map<String, List<String>> listMap = XpathHelper.parseContent(responseHtml, task.getExps().toArray(new String[0]));
        // 5. 存储数据
        // 是否添加到新的任务
    }
}
