package io.poten13.deepfocus.domain.task.repository;

import io.poten13.deepfocus.domain.task.dto.model.SubTaskModel;
import io.poten13.deepfocus.domain.task.entity.SubTask;

import java.util.List;

public interface SubTaskCustomRepository {
    List<SubTaskModel> getModelListByTaskIds(List<Long> taskIds);
    List<SubTask> getAllByTaskId(Long taskIds);
}
