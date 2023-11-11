package io.poten13.deepfocus.domain.common;

import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
public abstract class BaseTimeEntity {
    @Comment("생성날짜")
    @CreatedDate
    @Column(nullable = false, updatable = false, columnDefinition = "timestamp with time zone default CURRENT_TIMESTAMP")
    private LocalDateTime createdDateTime;

    @Comment("수정날짜")
    @LastModifiedDate
    @Column(nullable = false, columnDefinition = "timestamp with time zone default CURRENT_TIMESTAMP")
    private LocalDateTime modifiedDateTime;
}
