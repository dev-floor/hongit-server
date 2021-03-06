package com.devfloor.hongit.core.professor.domain

import com.devfloor.hongit.core.common.domain.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

/**
 * 강의를 진행하는 교수의 정보를 관리하는 entity
 *
 * @property id 아이디
 * @property name 이름
 * @property email 이메일
 */
@Entity
@Table(name = "professors")
class Professor(
    name: String,
    email: String? = null,
    id: Long = 0,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "professor_id")
    val id: Long = id

    @Column(name = "name")
    val name: String = name

    @Column(name = "email")
    var email: String? = email
        protected set

    fun withId(id: Long): Professor = Professor(name, email, id)
}
