package ua.kpi.fict.multinewscw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(basePackages = "ua.kpi.fict.multinewscw.repositories")
public class MultiNewsCwApplication {
    public static void main(String[] args) {
        SpringApplication.run(MultiNewsCwApplication.class, args);
    }
}
