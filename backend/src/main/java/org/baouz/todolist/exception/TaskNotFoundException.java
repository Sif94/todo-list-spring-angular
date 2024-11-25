package org.baouz.todolist.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
@Data
public class TaskNotFoundException extends RuntimeException {
    private final String message;
}
