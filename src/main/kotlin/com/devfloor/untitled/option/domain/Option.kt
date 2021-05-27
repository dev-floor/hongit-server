package com.devfloor.untitled.option.domain

import com.devfloor.untitled.common.domain.BaseEntity
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
 * @property type 옵션 종류
 */
@Entity
@Table(name = "options")
class Option(
    type: OptionType,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    val id: Long = 0

    @Column(name = "option_type")
    @Enumerated(value = EnumType.STRING)
    val type: OptionType = type
}
