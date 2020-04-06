package practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({
        @PropertySource(value = { "classpath:application.properties" }, ignoreResourceNotFound = true),
        @PropertySource(value = { "file:${external.config.location}/application.properties" }, ignoreResourceNotFound = true),

        @PropertySource(value = { "classpath:database.properties" }, ignoreResourceNotFound = true),
        @PropertySource(value = { "file:${external.config.location}/database.properties" }, ignoreResourceNotFound = true),
})
public class PracticeWebapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PracticeWebapiApplication.class, args);
    }

}
