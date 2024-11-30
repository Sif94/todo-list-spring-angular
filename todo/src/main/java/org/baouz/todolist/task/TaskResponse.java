package org.baouz.todolist.task;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Builder
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private Boolean isDone;
    private LocalDateTime dueDate;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;
}
