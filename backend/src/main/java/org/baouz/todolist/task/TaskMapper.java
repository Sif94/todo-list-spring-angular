package org.baouz.todolist.task;

import org.springframework.stereotype.Service;

@Service
public class TaskMapper {

    public TaskResponse toTaskResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .status(task.getStatus())
                .createdDate(task.getCreatedDate())
                .lastUpdatedDate(task.getLastModifiedDate())
                .build();
    }

    public Task toTask(TaskRequest request) {
        return Task.builder()
                .title(request.title())
                .description(request.description())
                .dueDate(request.dueDate())
                .build();
    }
}
