package com.devfloor.hongit.api.common.exception

import com.devfloor.hongit.core.common.domain.BaseEntity
import java.lang.RuntimeException
import kotlin.reflect.KClass

class UnprocessableEntityException(override val message: String) : RuntimeException(message) {
    companion object {
        fun <T : BaseEntity> throwAlreadyExists(clazz: KClass<T>, field: String, value: String): Nothing =
            throw UnprocessableEntityException("${field}에 해당하는 ${clazz.simpleName}이(가) 이미 존재합니다 - $field: $value")
    }
}
