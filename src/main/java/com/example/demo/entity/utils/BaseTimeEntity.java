package com.example.demo.entity.utils;

import lombok.Getter;
import net.bytebuddy.asm.Advice;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // Auditing : 자동으로 시간을 매핑하여 넣어줌
@Getter
public abstract class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createdTime;

    @LastModifiedBy
    private LocalDateTime lastModifiedTime;
}
