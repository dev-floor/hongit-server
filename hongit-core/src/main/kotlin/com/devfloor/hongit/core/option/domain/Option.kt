package com.devfloor.hongit.core.option.domain

import com.devfloor.hongit.core.common.domain.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

/**
 * 옵션 정보를 관리하는 entity
 *
 * @property id 아이디
 * @property text 옵션 설명
 * @property type 옵션 종류
 */
@Entity
@Table(name = "options")
class Option(
    text: String,
    type: OptionType,
    id: Long = 0,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    val id: Long = id

    @Column(name = "text")
    val text: String = text

    @Column(name = "option_type")
    @Enumerated(value = EnumType.STRING)
    val type: OptionType = type

    fun withId(id: Long): Option = Option(text, type, id)
}
