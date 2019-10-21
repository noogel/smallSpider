package noogel.xyz.controller;

import noogel.xyz.task.TaskManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @Autowired
    private TaskManager taskManager;

    @GetMapping("/")
    public String test() {
        return "ok";
    }
}
