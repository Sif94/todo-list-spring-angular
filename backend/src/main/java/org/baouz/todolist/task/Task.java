package org.baouz.todolist.task;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.baouz.todolist.commun.BaseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "tasks")
public class Task extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private LocalDateTime dueDate;
    @Enumerated(STRING)
    private TaskStatus status;
}
