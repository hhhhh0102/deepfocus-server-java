package io.poten13.deepfocus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EntityScan("io.poten13.deepfocus")
@EnableJpaAuditing
@EnableFeignClients
@SpringBootApplication
public class DeepfocusServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeepfocusServerApplication.class, args);
    }

}
