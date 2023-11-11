package io.poten13.deepfocus.domain.task.support;

import io.poten13.deepfocus.domain.task.controller.CreateSubTaskRequest;
import io.poten13.deepfocus.domain.task.controller.CreateTaskRequest;
import io.poten13.deepfocus.domain.task.controller.SubTaskResponse;
import io.poten13.deepfocus.domain.task.controller.TaskResponse;
import io.poten13.deepfocus.domain.task.controller.TaskStatsResponse;
import io.poten13.deepfocus.domain.task.controller.UpdateSubTaskRequest;
import io.poten13.deepfocus.domain.task.controller.UpdateTaskRequest;
import io.poten13.deepfocus.domain.task.dto.TaskDto;
import io.poten13.deepfocus.domain.task.dto.TaskStatsDto;
import io.poten13.deepfocus.domain.task.dto.command.CreateSubTaskCommand;
import io.poten13.deepfocus.domain.task.dto.command.CreateTaskCommand;
import io.poten13.deepfocus.domain.task.dto.command.UpdateTaskCommand;
import io.poten13.deepfocus.domain.task.dto.model.SubTaskModel;
import io.poten13.deepfocus.global.util.TimeUtils;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        imports = {TimeUtils.class})
public interface TaskMapper {

    @Mapping(target = "endTime", expression = "java(TimeUtils.addMinutesToUnixTimeStamp(request.getStartTime(), request.getSpanMinute()))")
    @Mapping(target = "startDate", expression = "java(TimeUtils.convertLocalDateFrom(request.getStartTime()))")
    @Mapping(target = "endDate", expression = "java(TimeUtils.convertLocalDateFrom(TimeUtils.addMinutesToUnixTimeStamp(request.getStartTime(), request.getSpanMinute())))")
    CreateTaskCommand from(CreateTaskRequest request);

    CreateSubTaskCommand from(CreateSubTaskRequest request);

    @Mapping(target = "endTime", expression = "java(TimeUtils.addMinutesToUnixTimeStamp(request.getStartTime(), request.getSpanMinute()))")
    @Mapping(target = "startDate", expression = "java(TimeUtils.convertLocalDateFrom(request.getStartTime()))")
    @Mapping(target = "endDate", expression = "java(TimeUtils.convertLocalDateFrom(TimeUtils.addMinutesToUnixTimeStamp(request.getStartTime(), request.getSpanMinute())))")
    UpdateTaskCommand from(UpdateTaskRequest request);

    // 서브 태스크 수정 요청 -> 서브 태스크 생성
    CreateSubTaskCommand from(UpdateSubTaskRequest request);

    TaskResponse from(TaskDto task);

    SubTaskResponse from(SubTaskModel subTask);

    TaskStatsResponse from(TaskStatsDto stats);
}
