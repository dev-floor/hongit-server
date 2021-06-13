package com.devfloor.untitled.common.domain

import com.devfloor.untitled.common.utils.LOCAL_DATE_TIME_FORMAT
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

/**
 * 공통 정보를 관리하는 entity
 *
 * @property createdAt 생성일
 * @property modifiedAt 수정일
 */
@MappedSuperclass
@EntityListeners(value = [AuditingEntityListener::class])
abstract class BaseEntity {
    @Column(name = "created_at")
    @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.MIN
        protected set

    @Column(name = "modified_at")
    @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
    @LastModifiedDate
    var modifiedAt: LocalDateTime = LocalDateTime.MIN
        protected set
}
