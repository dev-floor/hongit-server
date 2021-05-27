package com.devfloor.untitled.subject.domain

import com.devfloor.untitled.common.domain.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * 과목의 정보를 관리하는 entity
 *
 * @property id 아이디
 * @property name 과목명
 */
@Entity
class Subject(
    name: String,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    val id: Long = 0

    @Column(name = "name")
    val name: String = name
}
