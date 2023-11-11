package io.poten13.deepfocus.domain.task.client;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class OpenAIFeignConfig {

    @Value("${app.open-ai.api-key}")
    private String API_KEY;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Authorization", getOpenAISecretKey());
            requestTemplate.header("Content-Type", "application/json");
        };
    }

    private String getOpenAISecretKey() {
        return "Bearer " + API_KEY;
    }
}
