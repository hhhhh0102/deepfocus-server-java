package io.poten13.deepfocus.domain.task.support;

import io.poten13.deepfocus.domain.task.controller.CreateSubTaskRequest;
import io.poten13.deepfocus.domain.task.controller.CreateTaskRequest;
import io.poten13.deepfocus.domain.task.controller.SubTaskResponse;
import io.poten13.deepfocus.domain.task.controller.TaskResponse;
import io.poten13.deepfocus.domain.task.controller.UpdateSubTaskRequest;
import io.poten13.deepfocus.domain.task.controller.UpdateTaskRequest;
import io.poten13.deepfocus.domain.task.dto.TaskDto;
import io.poten13.deepfocus.domain.task.dto.command.CreateSubTaskCommand;
import io.poten13.deepfocus.domain.task.dto.command.CreateTaskCommand;
import io.poten13.deepfocus.domain.task.dto.command.UpdateTaskCommand;
import io.poten13.deepfocus.domain.task.dto.model.SubTaskModel;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TaskMapper {
    CreateTaskCommand from(CreateTaskRequest request);

    CreateSubTaskCommand from(CreateSubTaskRequest request);

    UpdateTaskCommand from(UpdateTaskRequest request);

    // 서브 태스크 수정 요청 -> 서브 태스크 생성
    CreateSubTaskCommand from(UpdateSubTaskRequest request);

    TaskResponse from(TaskDto task);

    SubTaskResponse from(SubTaskModel subTask);
}
