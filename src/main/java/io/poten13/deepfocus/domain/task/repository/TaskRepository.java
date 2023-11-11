package io.poten13.deepfocus.domain.task.repository;

import io.poten13.deepfocus.domain.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long>, TaskCustomRepository {
}
