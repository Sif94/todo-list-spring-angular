package org.baouz.todolist.task;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.baouz.todolist.commun.BaseEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "tasks")
public class Task extends BaseEntity implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private LocalDateTime dueDate;
    private Boolean isDone;
    private String ownerEmail;
    private Boolean hasBeenNotified;

}
