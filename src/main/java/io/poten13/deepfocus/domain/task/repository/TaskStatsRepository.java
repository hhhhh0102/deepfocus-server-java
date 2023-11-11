package io.poten13.deepfocus.domain.task.repository;

import io.poten13.deepfocus.domain.task.entity.TaskStats;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskStatsRepository extends MongoRepository<TaskStats, String> {
}
