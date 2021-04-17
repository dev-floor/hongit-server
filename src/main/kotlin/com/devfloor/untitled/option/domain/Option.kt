package com.devfloor.untitled.option.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

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
