package com.devfloor.untitled.common.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.MappedSuperclass

/**
 * 공통 정보를 관리하는 entity
 *
 * @property createdDate 생성일
 * @property modifiedDate 수정일
 */
@MappedSuperclass
abstract class BaseEntity {
    @Column(name = "created_date")
    @CreatedDate
    var createdDate: LocalDateTime = LocalDateTime.MIN
        protected set

    @Column(name = "modified_date")
    @LastModifiedDate
    var modifiedDate: LocalDateTime = LocalDateTime.MIN
        protected set
}