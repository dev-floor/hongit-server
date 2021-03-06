package com.devfloor.hongit.api.common.exception

import com.devfloor.hongit.core.common.domain.BaseEntity
import kotlin.reflect.KClass

class EntityNotFoundException(override val message: String) : RuntimeException(message) {
    companion object {
        fun <T : BaseEntity> notExistsId(clazz: KClass<T>, id: Long): Nothing =
            throw EntityNotFoundException("id에 해당하는 ${clazz.simpleName}이(가) 존재하지 않습니다 - ${clazz.simpleName}Id: $id")

        fun <T : BaseEntity> notExistsField(clazz: KClass<T>, field: String, value: String): Nothing =
            throw EntityNotFoundException("${field}에 해당하는 ${clazz.simpleName}이(가) 존재하지 않습니다 - $field: $value")
    }
}
