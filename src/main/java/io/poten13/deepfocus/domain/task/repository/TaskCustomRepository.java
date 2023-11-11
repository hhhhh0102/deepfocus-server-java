package io.poten13.deepfocus.domain.task.repository;

import io.poten13.deepfocus.domain.task.dto.model.TaskModel;

import java.time.LocalDate;
import java.util.List;

public interface TaskCustomRepository {
    List<TaskModel> findTaskMoelList(LocalDate date);
}
