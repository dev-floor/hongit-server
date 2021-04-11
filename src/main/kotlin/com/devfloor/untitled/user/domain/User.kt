package com.devfloor.untitled.user.domain

import javax.persistence.*

@Entity
@Table(name = "user")
class User(
    nickname: String,
    type: UserType,
    image: String,
    classOf: String?,
    github: String?,
    blog: String?,
    description: String?,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    var id: Long? = null
        private set

    @Column(name = "name")
    var nickname: String = nickname
        private set

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type")
    var type: UserType = type
        private set

    @Column(name = "image")
    var image: String = image
        private set

    @Column(name = "class_of")
    var classOf: String? = classOf
        private set

    @Column(name = "github")
    var github: String? = github
        private set

    @Column(name = "blog")
    var blog: String? = blog
        private set

    @Lob
    @Column(name = "description")
    var description: String? = description
        private set
}