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
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class TaskCustomRepositoryImpl implements TaskCustomRepository {

    private final JPAQueryFactory queryFactory;

    private static final QTask task = QTask.task;
    private static final QUser user = QUser.user;

    @Override
    public List<TaskModel> findTaskModelList(LocalDate date, String userId) {
        return queryFactory
                .select(getTaskModelProjection())
                .from(task)
                .join(task.user, user)
                .where(task.startDate.loe(date),
                        task.endDate.goe(date),
                        task.user.userId.eq(userId))
                .orderBy(task.startTime.asc())
                .fetch();
    }

    @Override
    public List<TaskModel> findByUserIdAndBetweenUnisTimeStamp(String userId, long startTime, long endTime) {
        return queryFactory
                .select(getTaskModelProjection())
                .from(task)
                .join(task.user, user)
                .where(user.userId.eq(userId),
                        betweenUnixTimeStamp(startTime, endTime))
                .fetch();
    }

    @Override
    public String getMonthLongestTaskTitle(String userId, int year, int month) {
        return queryFactory
                .select(task.title)
                .from(task)
                .join(task.user, user)
                .where(user.userId.eq(userId),
                        task.startDate.year().eq(year),
                        task.startDate.month().eq(month))
                .groupBy(task.title)
                .orderBy(task.spanMinute.sum().desc())
                .limit(1)
                .fetchOne();
    }

    @Override
    public Long getMonthTotalTaskCount(String userId, int year, int month) {
        return queryFactory
                .select(task.count())
                .from(task)
                .join(task.user, user)
                .where(user.userId.eq(userId),
                        task.startDate.year().eq(year),
                        task.startDate.month().eq(month))
                .fetchOne();
    }

    @Override
    public Long getMonthTotalSpanMinutes(String userId, int year, int month) {
        return queryFactory
                .select(task.spanMinute.sum())
                .from(task)
                .join(task.user, user)
                .where(user.userId.eq(userId),
                        task.startDate.year().eq(year),
                        task.startDate.month().eq(month))
                .fetchOne();
    }

    @Override
    public List<LocalDate> getMonthPerformedDates(String userId, int year, int month) {
        List<LocalDate> performedDates = new ArrayList<>();
        performedDates.addAll(
                queryFactory
                        .select(task.startDate)
                        .from(task)
                        .join(task.user, user)
                        .where(user.userId.eq(userId),
                                task.startDate.year().eq(year),
                                task.startDate.month().eq(month))
                        .fetch());
        performedDates.addAll(
                queryFactory
                        .select(task.endDate)
                        .from(task)
                        .join(task.user, user)
                        .where(task.user.userId.eq(userId),
                                task.endDate.year().eq(year),
                                task.endDate.month().eq(month))
                        .fetch());

        return performedDates.stream()
                .distinct()
                .sorted()
                .toList();
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
