package noogel.xyz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpiderWebMain {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpiderWebMain.class, args);
    }
}
