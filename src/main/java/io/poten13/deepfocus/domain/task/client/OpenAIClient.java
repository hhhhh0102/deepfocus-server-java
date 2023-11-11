package io.poten13.deepfocus.domain.task.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "OpenAI", url = "https://api.openai.com/v1/chat/completions", configuration = {OpenAIFeignConfig.class})
public interface OpenAIClient {

    @PostMapping
    OpenAIChatResponse createChatCompletion(@RequestBody OpenAIChatRequest request);
}
