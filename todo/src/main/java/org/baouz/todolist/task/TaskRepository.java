package org.baouz.todolist.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByCreatedBy(String createdBy, Pageable pageable);

    @Query("SELECT t FROM Task t WHERE t.dueDate > CURRENT_TIMESTAMP AND t.dueDate < :nextDay AND t.hasBeenNotified = false")
    List<Task> findTasksDueIn24Hours(@Param("nextDay") LocalDateTime nextDay);
}


