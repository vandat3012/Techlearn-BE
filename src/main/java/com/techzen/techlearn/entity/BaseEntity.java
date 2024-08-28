package com.techzen.techlearn.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Column(name="created_by",updatable = false)
    @CreatedBy
    private String createdBy;

    @Column(name="created_date", updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name="modified_date")
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @Column(name="modified_by")
    @LastModifiedBy
    private String modifiedBy;

}
