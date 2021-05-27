package com.devfloor.untitled.course.domain

enum class CourseTime(
    val number: Long,
    val startTime: String,
    val endTime: String,
) {
    FIRST(1, "09:00", "10:00"),
    SECOND(2, "10:00", "11:00"),
    THIRD(3, "11:00", "12:00"),
    FOURTH(4, "12:00", "13:00"),
    FIFTH(5, "13:00", "14:00"),
    SIXTH(6, "14:00", "15:00"),
    SEVENTH(7, "15:00", "16:00"),
    EIGHTH(8, "16:00", "17:00"),
    NINTH(9, "17:00", "18:00"),
    TENTH(10, "18:00", "19:00"),
    ELEVENTH(11, "19:00", "20:00"),
    TWELFTH(12, "20:00", "21:00"),
    THIRTEENTH(13, "21:00", "22:00"),
    FOURTEENTH(14, "22:00", "23:00"),
    FIFTEENTH(15, "23:00", "24:00"),
    ;

    companion object {
        fun from(number: Long) = values().first { it.number == number }
    }
}
