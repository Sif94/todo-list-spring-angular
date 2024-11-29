package org.baouz.todolist.task;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.baouz.todolist.commun.PageResponse;
import org.baouz.todolist.exception.OperationNotPermittedException;
import org.baouz.todolist.notification.NotificationProducer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final RabbitTemplate rabbitTemplate;
    private final TaskRepository repository;
    private final TaskMapper mapper;
    private final NotificationProducer producer;


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

    public Long createTask(TaskRequest request, Authentication connectedUser) {
        Map<String, Object> attributes;
        attributes = ((JwtAuthenticationToken) connectedUser).getTokenAttributes();
        String email = (String) attributes.get("email");

        Task task = mapper.toTask(request);
        task.setOwnerEmail(email);
        task.setStatus(TaskStatus.CREATED);
        task.setHasBeenNotified(false);
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


    //@Scheduled(cron = "0 0 * * * *") // Runs every hour
    //@Scheduled(cron = "0 */2 * * * *")
    @Scheduled(cron = "0 * * * * *") // Runs every minute for testing
    public void notifyDueTasks() {
        log.info("Notifying due tasks");
        List<Task> tasks = repository.findTasksDueIn24Hours(LocalDateTime.now().plusMinutes(3));
        //List<Task> tasks = repository.findAll();
        for (Task task : tasks) {
            log.info(task.toString());
            producer.sendNotification(task);
            task.setHasBeenNotified(true);
            repository.save(task);
        }
    }
}
