package com.devfloor.untitled.course.domain

import com.devfloor.untitled.common.domain.Grade
import com.devfloor.untitled.common.domain.OpeningSemester
import com.devfloor.untitled.common.domain.Semester
import com.devfloor.untitled.option.domain.Option
import com.devfloor.untitled.option.domain.OptionType
import com.devfloor.untitled.professor.domain.Professor
import com.devfloor.untitled.subject.domain.Subject
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.time.Year

internal class CourseTest {
    @Test
    internal fun `init - 옵션이 분반 옵션이 아닐 경우 예외 발생`() {
        // given
        val option = Option("질문", OptionType.ARTICLE_KIND)
        val timetable = Timetable(listOf(Schedule.from("월1"), Schedule.from("화2")))

        // when-then
        assertThatThrownBy {
            Course(
                code = "code",
                openingSemester = OpeningSemester(Year.now(), Semester.FIRST_SEMESTER),
                professor = Professor("professor", "test@test.com"),
                subject = Subject("subject"),
                grade = Grade.FRESHMAN,
                option = option,
                timetable = timetable
            )
        }.isInstanceOf(AssertionError::class.java)
    }
}
