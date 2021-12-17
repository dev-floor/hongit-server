package com.devfloor.hongit.api.course.application

import com.devfloor.hongit.api.support.MockitoHelper.any
import com.devfloor.hongit.api.support.TestFixtures.BoardFixture.COURSE_BOARD_1
import com.devfloor.hongit.api.support.TestFixtures.CourseFixture.COURSE_1
import com.devfloor.hongit.api.support.TestFixtures.CourseFixture.COURSE_2
import com.devfloor.hongit.api.support.TestFixtures.CourseFixture.COURSE_3
import com.devfloor.hongit.core.course.domain.CourseRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class CourseServiceTest {
    @InjectMocks
    private lateinit var courseService: CourseService

    @Mock
    private lateinit var courseRepository: CourseRepository

    @BeforeEach
    fun setUp() {
        courseService = CourseService(courseRepository)
    }

    @Test
    internal fun `showAllByBoard - 수업 정보 조회에 성공하고 course list를 반환한다`() {
        val courses = listOf(COURSE_1, COURSE_2)
        `when`(courseRepository.findAllByBoard(any())).thenReturn(courses)

        val result = courseService.showAllByBoard(COURSE_BOARD_1)

        assertThat(result).isEqualTo(courses)
    }

    @Test
    internal fun `showAllByBoard - 수업 정보가 동일하지 않아 수업 정보 조회에 실패한다`() {
        val courses = listOf(COURSE_1, COURSE_3)
        `when`(courseRepository.findAllByBoard(any())).thenReturn(courses)
        val errorMessage = "수업 정보가 동일하지 않습니다."

        assertThatThrownBy { courseService.showAllByBoard(COURSE_BOARD_1) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(errorMessage)
    }
}
