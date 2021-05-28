package com.devfloor.untitled.common.domain

enum class Grade(
    val number: Long,
) {
    FRESHMAN(1),
    SOPHOMORE(2),
    JUNIOR(3),
    SENIOR(4),
    ;

    val text: String = "${number}학년"
}
