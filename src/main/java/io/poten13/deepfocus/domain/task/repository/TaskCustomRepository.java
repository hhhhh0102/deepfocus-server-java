package io.poten13.deepfocus.domain.task.repository;

import io.poten13.deepfocus.domain.task.dto.model.TaskModel;

import java.time.LocalDate;
import java.util.List;

public interface TaskCustomRepository {
    List<TaskModel> findTaskModelList(LocalDate date, String userId);

    List<TaskModel> findByUserIdAndBetweenUnisTimeStamp(String userId, long startTime, long endTime);

    String getMonthLongestTaskTitle(String userId, int year, int month);

    Long getMonthTotalTaskCount(String userId, int year, int month);

    Long getMonthTotalSpanMinutes(String userId, int year, int month);

    List<LocalDate> getMonthPerformedDates(String userId, int year, int month);
}
