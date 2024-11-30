package org.baouz.todolist.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
@Data
public class OperationNotPermittedException extends RuntimeException {
    private final String message;
}
