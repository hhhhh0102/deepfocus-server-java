package io.poten13.deepfocus.domain.task.controller;

import io.poten13.deepfocus.domain.task.dto.TaskDto;
import io.poten13.deepfocus.domain.task.dto.TaskStatsDto;
import io.poten13.deepfocus.domain.task.dto.command.CreateTaskCommand;
import io.poten13.deepfocus.domain.task.dto.command.UpdateTaskCommand;
import io.poten13.deepfocus.domain.task.service.TaskService;
import io.poten13.deepfocus.domain.task.support.TaskMapper;
import io.poten13.deepfocus.domain.user.dto.UserModel;
import io.poten13.deepfocus.domain.user.service.UserService;
import io.poten13.deepfocus.global.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.List;

@Validated
@Tag(name = "Task", description = "태스크 관리 API")
@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    private final UserService userService;


    @GetMapping
    @Operation(summary = "태스크 목록 조회")
    public ApiResponse<List<TaskResponse>> getTaskList(
            @RequestParam("date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        String userToken = SecurityContextHolder.getContext().getAuthentication().getName();
        UserModel user = userService.getByUserToken(userToken);

        List<TaskDto> tasks = taskService.getTaskList(date, user.getUserId());
        List<TaskResponse> response = tasks.stream().map(taskMapper::from).toList();
        return ApiResponse.ok(response);
    }

    @PostMapping
    @Operation(summary = "태스크 생성")
    public ApiResponse<TaskResponse> createTask(@RequestBody CreateTaskRequest request) {
        String userToken = SecurityContextHolder.getContext().getAuthentication().getName();
        UserModel user = userService.getByUserToken(userToken);

        CreateTaskCommand command = taskMapper.from(request);

        Long taskId = taskService.createTask(command, user.getUserId());
        TaskDto task = taskService.getTaskById(taskId);
        return ApiResponse.ok(taskMapper.from(task));
    }

    @PutMapping("/{taskId}")
    @Operation(summary = "태스크 수정")
    public ApiResponse<TaskResponse> updateTask(@PathVariable Long taskId, @RequestBody UpdateTaskRequest request) {
        String userToken = SecurityContextHolder.getContext().getAuthentication().getName();
        UserModel user = userService.getByUserToken(userToken);

        UpdateTaskCommand command = taskMapper.from(request);
        taskService.updateTask(taskId, command, user.getUserId());
        TaskDto task = taskService.getTaskById(taskId);
        return ApiResponse.ok(taskMapper.from(task));
    }

    @DeleteMapping("/{taskId}")
    @Operation(summary = "태스크 삭제")
    public ApiResponse<String> deleteTask(@PathVariable Long taskId) {
        String userToken = SecurityContextHolder.getContext().getAuthentication().getName();
        UserModel user = userService.getByUserToken(userToken);

        taskService.deleteTask(taskId, user.getUserId());
        return ApiResponse.success();
    }

    @GetMapping("/recommendations")
    @Operation(summary = "서브 태스크 추천")
    public ApiResponse<List<SubTaskRecommendResponse>> recommendSubTasks(
            @RequestParam("limit") @Min(1) @Max(5) int limit,
            @RequestParam("task") String taskTitle) {
        List<String> subTaskTitleList = taskService.recommendSubTaskTitleList(limit, taskTitle);
        return ApiResponse.ok(subTaskTitleList.stream()
                .map(SubTaskRecommendResponse::new)
                .toList());
    }

    @GetMapping("/statistics")
    @Operation(summary = "태스크 통계 조회")
    public ApiResponse<TaskStatsResponse> getTaskStats(
            @RequestParam("year") @Min(1900) @Max(2038) int year,
            @RequestParam("month") @Min(1) @Max(12) int month) {
        String userToken = SecurityContextHolder.getContext().getAuthentication().getName();
        UserModel user = userService.getByUserToken(userToken);
        TaskStatsDto stats = taskService.getUserMonthlyTaskStats(user.getUserId(), year, month);
        TaskStatsResponse response = taskMapper.from(stats);
        return ApiResponse.ok(response);
    }
}