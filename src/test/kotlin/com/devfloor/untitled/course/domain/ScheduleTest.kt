package com.devfloor.untitled.course.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ScheduleTest {
    @Test
    internal fun `from - 시간표 단건 문자열을 입력 시 객체 반환`() {
        // given
        val scheduleText = "월1"

        // when
        val schedule = Schedule.from(scheduleText)

        // then
        assertThat(schedule.courseDay).isEqualTo(CourseDay.MONDAY)
        assertThat(schedule.courseTime).isEqualTo(CourseTime.FIRST)
    }

    @Test
    internal fun `CACHE - 같은 시간표 단건에 대한 객체 반환시 동일한 객체를 반환`() {
        // given
        val scheduleText = "월1"

        // when
        val schedule1 = Schedule.from(scheduleText)
        val schedule2 = Schedule.from(scheduleText)

        // then
        assertThat(schedule1).isEqualTo(schedule2)
    }
}
