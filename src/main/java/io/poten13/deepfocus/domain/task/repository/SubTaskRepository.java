package io.poten13.deepfocus.domain.task.repository;

import io.poten13.deepfocus.domain.task.entity.SubTask;
import io.poten13.deepfocus.domain.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubTaskRepository extends JpaRepository<SubTask, Long>, SubTaskCustomRepository {
    List<SubTask> findAllByTask(Task task);
}
