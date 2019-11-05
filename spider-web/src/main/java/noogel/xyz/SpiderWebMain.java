package noogel.xyz;

import noogel.xyz.processor.DefaultTaskProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpiderWebMain {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(SpiderWebMain.class, args);
        DefaultTaskProcessor taskProcessor = context.getBean(DefaultTaskProcessor.class);
        taskProcessor.run(args);
    }
}
