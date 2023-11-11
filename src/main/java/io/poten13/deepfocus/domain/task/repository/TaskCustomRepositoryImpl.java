package io.poten13.deepfocus.domain.task.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.poten13.deepfocus.domain.task.dto.model.QTaskModel;
import io.poten13.deepfocus.domain.task.dto.model.TaskModel;
import io.poten13.deepfocus.domain.task.entity.QTask;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class TaskCustomRepositoryImpl implements TaskCustomRepository {

    private final JPAQueryFactory queryFactory;

    private static final QTask task = QTask.task;

    @Override
    public List<TaskModel> findTaskMoelList(LocalDate date) {
        return queryFactory
                .select(getTaskModelProjection())
                .from(task)
                .where(task.startDate.loe(date),
                        task.endDate.goe(date))
                .fetch();
    }

    private Expression<TaskModel> getTaskModelProjection() {
        return new QTaskModel(task.taskId, task.title, task.startTime, task.endTime,
                task.spanMinute, task.startDate, task.endDate);
    }

}
