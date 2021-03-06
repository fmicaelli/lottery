package me.illian.euromillions;

import me.illian.euromillions.jooq.JooqConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jooq.JooqAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {JooqAutoConfiguration.class})
@Import(JooqConfiguration.class)
@PropertySource("classpath:file.properties")
@EnableScheduling
@EnableAsync
public class EuromillionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EuromillionsApplication.class, args);
    }
}
