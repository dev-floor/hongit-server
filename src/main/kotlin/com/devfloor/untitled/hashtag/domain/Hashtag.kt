package com.devfloor.untitled.hashtag.domain

import com.devfloor.untitled.common.domain.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

/**
 * 해시태그 정보를 관리하는 entity
 *
 * @property id 아이디
 * @property name 해시태그 이름
 */
@Entity
@Table(name = "hashtags")
class Hashtag(
    name: String,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    val id: Long = 0

    @Column(name = "name")
    var name: String = name
        protected set
}
