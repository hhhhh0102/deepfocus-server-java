package io.poten13.deepfocus.domain.task.client;

import io.poten13.deepfocus.global.constants.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.MessageFormat;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OpenAIChatRequest {
    private final String model = "gpt-4";
    private List<MessageRequest> messages;

    public OpenAIChatRequest(int limit, String taskTitle) {
        this.messages = List.of(new MessageRequest("system", Constants.OPEN_AI_CHAT_REQUEST_SYSTEM_MESSAGE_ONE),
                new MessageRequest("system", Constants.OPEN_AI_CHAT_REQUEST_SYSTEM_MESSAGE_TWO),
                new MessageRequest("user", MessageFormat.format(Constants.OPEN_AI_CHAT_REQUEST_USER_MESSAGE_PATTERN, limit, taskTitle)));
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MessageRequest {
        private String role;
        private String content;
    }
}
