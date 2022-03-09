package SpringApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import SpringApplication.save.ApplicationConfig;

@SpringBootApplication
public class MainSpringApplication {

    public static void main(String[] args) throws Throwable {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        SpringApplication.run(MainSpringApplication.class);
    }
}