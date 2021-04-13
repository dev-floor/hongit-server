package com.devfloor.untitled.hashtag.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "hashtag")
open class Hashtag(
    name: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    var id: Long? = null
        protected set

    @Column(name = "name")
    var name: String = name
        protected set
}