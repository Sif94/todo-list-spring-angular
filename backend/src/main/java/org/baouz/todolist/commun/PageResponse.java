package org.baouz.todolist.commun;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PageResponse<T> {

    private int page;
    private int size;
    private Long totalElements;
    private Integer totalPages;
    private List<T> content;
    private Boolean isFirst;
    private Boolean isLast;

}
