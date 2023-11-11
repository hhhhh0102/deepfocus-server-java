package io.poten13.deepfocus.domain.task.entity;

import io.poten13.deepfocus.domain.task.dto.TaskStatsDto;
import io.poten13.deepfocus.global.constants.Constants;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;

@Getter
@Document
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaskStats {

    @Id
    private String id;

    private String userId;
    private Integer year;
    private Integer month;

    private String longestTask;
    private Long totalCount;
    private Long totalSpanMinutes;

    private List<LocalDate> performedDates;

    public static TaskStats from(TaskStatsDto command) {
        TaskStats taskStats = new TaskStats();
        taskStats.id = MessageFormat.format(Constants.TASK_STATS_KEY_PATTERN,
                command.getUserId(), command.getYear(), command.getMonth());
        taskStats.userId = command.getUserId();
        taskStats.year = command.getYear();
        taskStats.month = command.getMonth();
        taskStats.longestTask = command.getLongestTask();
        taskStats.totalCount = command.getTotalTaskCount();
        taskStats.totalSpanMinutes = command.getTotalSpanMinutes();
        taskStats.performedDates = command.getPerformedDates();
        return taskStats;
    }
}
