package pl.edu.agh.reporting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ReportingCoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReportingCoreApplication.class, args);
    }
}
