package ua.kpi.fict.multinewscw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MultiNewsCwApplication {
    public static void main(String[] args) {
        SpringApplication.run(MultiNewsCwApplication.class, args);
    }
}
