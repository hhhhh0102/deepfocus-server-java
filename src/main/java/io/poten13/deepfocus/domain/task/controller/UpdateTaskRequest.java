package io.poten13.deepfocus.domain.task.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "태스크 생성 요청")
public class UpdateTaskRequest {
    @Schema(description = "태스크 제목", example = "책 읽기")
    private String title;
    @Schema(description = "태스크 시작 시간 (unixTimestamp)", example = "1699224390")
    private int startTime;
    @Schema(description = "태스크 수행 시간 (단위: 분)", example = "30")
    private int spanMinute;
    @Schema(description = "태스크 의 하위 태스크 목록")
    List<UpdateSubTaskRequest> subTasks;
}
