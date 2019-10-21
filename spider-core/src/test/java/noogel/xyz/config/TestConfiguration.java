package noogel.xyz.config;

import noogel.xyz.AppBoot;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
@TestComponent
@SpringBootTest(classes = {AppBoot.class})
public @interface TestConfiguration {
}
