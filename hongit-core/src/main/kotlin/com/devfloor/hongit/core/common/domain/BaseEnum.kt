package com.devfloor.hongit.core.common.domain

/**
 * enum 객체들의 일관적인 형식을 맞추기 위한 interface
 *
 * @property id 객체 name
 * @property text 객체 설명
 */
interface BaseEnum {
    val id: String
    val text: String
}
