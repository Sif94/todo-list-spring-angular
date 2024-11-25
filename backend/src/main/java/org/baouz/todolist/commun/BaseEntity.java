package org.baouz.todolist.commun;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
@Data
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdDate;
    @LastModifiedDate
    @Column(insertable = false, nullable = false)
    private LocalDateTime lastModifiedDate;
    @CreatedBy
    @Column(updatable = false, nullable = false)
    private String createdBy;
    @LastModifiedBy
    @Column(insertable = false)
    private String lastModifiedBy;
}
