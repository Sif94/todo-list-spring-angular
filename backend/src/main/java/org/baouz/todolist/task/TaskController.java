package org.baouz.todolist.task;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.baouz.todolist.commun.PageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tasks")
@RequiredArgsConstructor
@Tag(name = "Task")
public class TaskController {
    private final TaskService service;

    @GetMapping
    public ResponseEntity<PageResponse<TaskResponse>> getTasksByUser(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.getTasksByUser(connectedUser, page, size));
    }
    @PostMapping
    public ResponseEntity<Long> addTask(
            @RequestBody @Valid TaskRequest request, Authentication connectedUser){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createTask(request, connectedUser));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Long> updateTaskStatus(
            @PathVariable Long id,
            TaskStatus status,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.updateTaskStatus(id, status, connectedUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateTaskById(
            @PathVariable Long id,
            @RequestBody @Valid TaskRequest request,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.updateTaskById(id, request, connectedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTaskById(
            @PathVariable Long id,
            Authentication connectedUser
    ){
        service.deleteTaskById(id, connectedUser);
        return ResponseEntity.noContent().build();
    }
}
