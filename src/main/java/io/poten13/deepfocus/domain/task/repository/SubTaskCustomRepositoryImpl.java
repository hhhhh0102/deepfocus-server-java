package io.poten13.deepfocus.domain.task.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.poten13.deepfocus.domain.task.dto.model.QSubTaskModel;
import io.poten13.deepfocus.domain.task.dto.model.SubTaskModel;
import io.poten13.deepfocus.domain.task.entity.QSubTask;
import io.poten13.deepfocus.domain.task.entity.QTask;
import io.poten13.deepfocus.domain.task.entity.SubTask;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SubTaskCustomRepositoryImpl implements SubTaskCustomRepository {

    private final JPAQueryFactory queryFactory;

    private static final QTask task = QTask.task;
    private static final QSubTask subtask = QSubTask.subTask;

    @Override
    public List<SubTaskModel> getModelListByTaskIds(List<Long> taskIds) {
        return queryFactory
                .select(getSubTaskModelProjection())
                .from(subtask)
                .join(subtask.task, task)
                .where(task.taskId.in(taskIds))
                .fetch();
    }

    @Override
    public List<SubTask> getAllByTaskId(Long taskIds) {
        return queryFactory
                .selectFrom(subtask)
                .join(subtask.task, task)
                .where(task.taskId.eq(taskIds))
                .fetch();
    }

    private Expression<SubTaskModel> getSubTaskModelProjection() {
        return new QSubTaskModel(subtask.subTaskId, subtask.title, task.taskId);
    }
}
