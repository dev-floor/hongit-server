package com.devfloor.untitled.user.domain

import javax.persistence.*

@Entity
@Table(name = "user")
class User(
    id: Long = 0,
    name: String,
    type: UserType,
    image: String = "",
    classOf: String? = null,
    github: String? = null,
    blog: String? = null,
    description: String? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val id: Long = id

    @Column(name = "name")
    val name: String = name

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type")
    val type: UserType = type

    @Column(name = "image")
    val image: String = image

    @Column(name = "class_of")
    val classOf: String? = classOf

    @Column(name = "github")
    val github: String? = github

    @Column(name = "blog")
    val blog: String? = blog

    @Lob
    @Column(name = "description")
    val description: String? = description
}