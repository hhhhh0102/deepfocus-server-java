package io.poten13.deepfocus.domain.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "NicknameGenerator", url = "https://nickname.hwanmoo.kr")
public interface NicknameGeneratorClient {

    @GetMapping(value = "/", consumes = "text/plain")
    String generateNickname(
            @RequestParam("format") String format,
            @RequestParam("count") int count,
            @RequestParam("max_length") int maxLength
    );
}
