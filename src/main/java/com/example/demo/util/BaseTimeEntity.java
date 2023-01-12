package com.example.demo.util;

import javax.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseTimeEntity {

    @CreatedDate
    @Column(updatable = false)
//    @Convert(converter = PersistenceDateConverter.class)
    private LocalDateTime createdDate;

    @LastModifiedDate
//    @Convert(converter = PersistenceDateConverter.class)
    private LocalDateTime modifiedDate;
}
