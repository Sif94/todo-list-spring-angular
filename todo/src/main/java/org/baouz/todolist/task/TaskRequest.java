package org.baouz.todolist.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TaskRequest(
        @NotNull(message = "Task title is mandatory")
        @NotEmpty(message = "Task title is mandatory")
        @NotBlank(message = "Task title is mandatory")
        String title,
        @NotNull(message = "Task description is mandatory")
        @NotEmpty(message = "Task description is mandatory")
        @NotBlank(message = "Task description is mandatory")
        String description,
        @NotNull(message = "Task due date is mandatory")
        LocalDateTime dueDate
) {
}
