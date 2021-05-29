package com.devfloor.untitled.board.domain

import com.devfloor.untitled.common.domain.OpeningSemester
import com.devfloor.untitled.professor.domain.Professor
import com.devfloor.untitled.subject.domain.Subject
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "boards")
class Board(
    professor: Professor,
    subject: Subject,
    openingSemester: OpeningSemester,
    type: BoardType,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    val id: Long = 0

    @ManyToOne
    @JoinColumn(name = "professor_id")
    val professor: Professor = professor

    @ManyToOne
    @JoinColumn(name = "subject_id")
    val subject: Subject = subject

    @Embedded
    @Column(name = "opening_semester")
    val openingSemester: OpeningSemester = openingSemester

    @Enumerated(value = EnumType.STRING)
    @Column(name = "board_type")
    val type: BoardType = type
}
