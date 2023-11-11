package io.poten13.deepfocus.domain.task.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSubTaskRequest {
    @Schema(description = "서브 태스크 제목", example = "책 가져오기")
    private String title;
}
