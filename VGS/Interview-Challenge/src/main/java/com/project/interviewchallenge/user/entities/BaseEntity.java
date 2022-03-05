package com.project.interviewchallenge.user.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.*;
import org.springframework.data.annotation.Version;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @CreatedDate
    private LocalDateTime createdOn;

    @LastModifiedDate
    private LocalDateTime lastModifiedOn;

    @Version
    private int version;

    private boolean isDeleted = false;

    public String tableName() {
        return tableName(getClass());
    }

    public static <T extends BaseEntity> String tableName(Class<T> entityClass) {
        Table table = entityClass.getAnnotation(Table.class);
        return table == null ? null : table.name();
    }
}

