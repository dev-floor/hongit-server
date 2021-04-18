package com.devfloor.untitled.option.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

/**
 * 게시물 정보를 관리하는 entity
 *
 * @property id 아이디
 * @property type 옵션 종류
 */
@Entity
@Table(name = "option")
class Option(
    type: OptionType
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    var id: Long? = null
        protected set

    @Column(name = "option_type")
    @Enumerated(value = EnumType.STRING)
    val type: OptionType = type
}
