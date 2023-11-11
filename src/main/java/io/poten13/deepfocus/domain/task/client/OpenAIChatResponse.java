package io.poten13.deepfocus.domain.task.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OpenAIChatResponse {
    private String id;
    private String object;
    private long created;
    private String model;
    private List<ChoiceResponse> choices;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChoiceResponse {
        private long index;
        private MessageResponse message;

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class MessageResponse {
            private String role;
            private String content;
        }
    }
}

/*
{
  "id": "chatcmpl-123",
  "object": "chat.completion",
  "created": 1677652288,
  "model": "gpt-3.5-turbo-0613",
  "system_fingerprint": "fp_44709d6fcb",
  "choices": [{
    "index": 0,
    "message": {
      "role": "assistant",
      "content": "\n\nHello there, how may I assist you today?",
    },
    "finish_reason": "stop"
  }],
  "usage": {
    "prompt_tokens": 9,
    "completion_tokens": 12,
    "total_tokens": 21
  }
}

 */