package com.devfloor.untitled.common.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.MappedSuperclass

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