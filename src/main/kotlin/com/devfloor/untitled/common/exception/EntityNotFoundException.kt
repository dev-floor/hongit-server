package com.devfloor.untitled.common.exception

import com.devfloor.untitled.common.domain.BaseEntity
import kotlin.reflect.KClass

class EntityNotFoundException(override val message: String) : RuntimeException() {
    companion object {
        fun <T : BaseEntity> notExistsId(clazz: KClass<T>, id: Long): Nothing =
            throw EntityNotFoundException(
                "id에 해당하는 ${clazz.simpleName}이(가) 존재하지 않습니다 - ${clazz.simpleName}Id: $id"
            )
    }
}
