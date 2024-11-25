package org.baouz.todolist.task;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.baouz.todolist.commun.PageResponse;
import org.baouz.todolist.exception.OperationNotPermittedException;
import org.baouz.todolist.exception.TaskNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repository;
    private final TaskMapper mapper;


    public PageResponse<TaskResponse> getTasksByUser(Authentication connectedUser, int page, int size) {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Task> taskPage = repository.findByCreatedBy(connectedUser.getName(), pageRequest);
        List<TaskResponse> content = taskPage.getContent()
                .stream()
                .map(mapper::toTaskResponse)
                .toList();
        return PageResponse.<TaskResponse>builder()
                .page(page)
                .size(size)
                .totalElements(taskPage.getTotalElements())
                .totalPages(taskPage.getTotalPages())
                .content(content)
                .isFirst(taskPage.isFirst())
                .isLast(taskPage.isLast())
                .build();

    }

    public Long createTask(TaskRequest request) {
        Task task = mapper.toTask(request);
        task.setStatus(TaskStatus.CREATED);
        return repository.save(task).getId();

    }

    public Long updateTaskStatus(Long id, TaskStatus status, Authentication connectedUser) {
        Task task = repository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(format("Task with ID:: %s was not found", id))
                );
        if(!Objects.equals(connectedUser.getName(), task.getCreatedBy())){
            throw new OperationNotPermittedException("You cannot update others tasks status");
        }
        task.setStatus(status);
        return repository.save(task).getId();
    }

    public Long updateTaskById(Long id, TaskRequest request, Authentication connectedUser) {
        Task task = repository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(format("Task with ID:: %s was not found", id))
                );
        if(!Objects.equals(connectedUser.getName(), task.getCreatedBy())){
            throw new OperationNotPermittedException("You cannot update others tasks");
        }
        Task newTask = mapper.toTask(request);
        newTask.setId(id);
        return repository.save(newTask).getId();

    }

    public void deleteTaskById(Long id, Authentication connectedUser) {
        Task task = repository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(format("Task with ID:: %s was not found", id))
                );
        if(!Objects.equals(connectedUser.getName(), task.getCreatedBy())){
            throw new OperationNotPermittedException("You cannot update others tasks status");
        }
        repository.deleteById(id);
    }
}
