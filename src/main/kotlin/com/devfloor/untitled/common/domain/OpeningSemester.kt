package com.devfloor.untitled.common.domain

import java.time.Year
import javax.persistence.Column
import javax.persistence.Embeddable

/**
 * 개설학기 정보를 관리하는 embedded 객체
 *
 * @property year 개설년도
 * @property semester 개설학기
 */
@Embeddable
class OpeningSemester(
    year: Year,
    semester: Semester,
) {
    @Column(name = "year")
    val year: Year = year

    @Column(name = "semester")
    val semester: Semester = semester
}
