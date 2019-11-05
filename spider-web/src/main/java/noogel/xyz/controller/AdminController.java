package noogel.xyz.controller;

import noogel.xyz.dto.PageQueueTaskDto;
import noogel.xyz.queue.SpiderScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @Autowired
    private SpiderScheduler spiderScheduler;

    @GetMapping("/")
    public String test() {
        PageQueueTaskDto pageQueueTaskDto = PageQueueTaskDto.of("http://zhidao.baidu.com/daily", "//body//@href");
        spiderScheduler.addTask(pageQueueTaskDto);
        return "ok";
    }
}
