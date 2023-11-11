package io.poten13.deepfocus.domain.task.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.poten13.deepfocus.domain.task.dto.model.QTaskModel;
import io.poten13.deepfocus.domain.task.dto.model.TaskModel;
import io.poten13.deepfocus.domain.task.entity.QTask;
import io.poten13.deepfocus.domain.user.entity.QUser;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class TaskCustomRepositoryImpl implements TaskCustomRepository {

    private final JPAQueryFactory queryFactory;

    private static final QTask task = QTask.task;
    private static final QUser user = QUser.user;

    // todo : 정렬 조건 수정
    @Override
    public List<TaskModel> findTaskModelList(LocalDate date, Long userId) {
        return queryFactory
                .select(getTaskModelProjection())
                .from(task)
                .join(task.user, user)
                .where(task.startDate.loe(date),
                        task.endDate.goe(date),
                        task.user.userId.eq(userId))
                .fetch();
    }

    @Override
    public List<TaskModel> findByUserIdAndBetweenUnisTimeStamp(Long userId, long startTime, long endTime) {
        return queryFactory
                .select(getTaskModelProjection())
                .from(task)
                .join(task.user, user)
                .where(user.userId.eq(userId),
                        betweenUnixTimeStamp(startTime, endTime))
                .fetch();
    }

    private BooleanExpression betweenUnixTimeStamp(long startTime, long endTime) {
        return isOverlappingWithStartTime(startTime)
                .or(isOverlappingWithEndTime(endTime))
                .or(isWithinTimeRange(startTime, endTime));
    }

    private BooleanExpression isOverlappingWithStartTime(long startTime) {
        return task.startTime.loe(startTime).and(task.endTime.goe(startTime));
    }

    private BooleanExpression isOverlappingWithEndTime(long endTime) {
        return task.startTime.loe(endTime).and(task.endTime.goe(endTime));
    }

    private BooleanExpression isWithinTimeRange(long startTime, long endTime) {
        return task.startTime.goe(startTime).and(task.endTime.loe(endTime));
    }

    private Expression<TaskModel> getTaskModelProjection() {
        return new QTaskModel(task.taskId, task.title, task.startTime, task.endTime,
                task.spanMinute, task.startDate, task.endDate, user.userId);
    }

}
