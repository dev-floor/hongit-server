package com.devfloor.hongit.core.subject.domain

import com.devfloor.hongit.core.common.domain.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

/**
 * 과목의 정보를 관리하는 entity
 *
 * @property id 아이디
 * @property name 과목명
 */
@Entity
@Table(name = "subjects")
class Subject(
    id: Long = 0,
    name: String,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    val id: Long = id

    @Column(name = "name")
    val name: String = name
}
