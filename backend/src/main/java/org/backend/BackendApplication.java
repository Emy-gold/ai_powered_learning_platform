package org.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);

        System.out.println(">>> DB_HOST=" + System.getenv("DB_HOST"));
        System.out.println(">>> DB_NAME=" + System.getenv("DB_NAME"));
        System.out.println(">>> DB_PASSWORD=" + (System.getenv("DB_PASSWORD") != null ? "[présent]" : "null"));
    }

}
