package com.devfloor.untitled.common.domain

/**
 * 학년 종류를 관리하는 enum
 *
 * @property number 학년
 * @property text 학년 설명
 */
enum class Grade(
    val number: Long,
) : BaseEnum {
    FRESHMAN(1),
    SOPHOMORE(2),
    JUNIOR(3),
    SENIOR(4),
    ;

    override val id: String
        get() = this.name

    override val text: String
        get() = "${number}학년"
}
