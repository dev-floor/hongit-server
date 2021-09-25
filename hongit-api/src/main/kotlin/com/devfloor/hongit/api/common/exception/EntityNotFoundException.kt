package com.devfloor.hongit.api.common.exception

import com.devfloor.hongit.core.common.domain.BaseEntity
import com.devfloor.hongit.core.user.domain.User
import kotlin.reflect.KClass

class EntityNotFoundException(override val message: String) : RuntimeException(message) {
    companion object {
        fun <T : BaseEntity> notExistsId(clazz: KClass<T>, id: Long): Nothing =
            throw EntityNotFoundException("id에 해당하는 ${clazz.simpleName}이(가) 존재하지 않습니다 - ${clazz.simpleName}Id: $id")

        fun notExistsNickname(clazz: KClass<User>, nickname: String): Nothing =
            throw EntityNotFoundException(
                "nickname에 해당하는 ${clazz.simpleName}이(가) 존재하지 않습니다 - ${clazz.simpleName} nickname : $nickname"
            )
    }
}
