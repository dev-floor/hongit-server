package com.devfloor.hongit.api.common.exception

import com.devfloor.hongit.core.user.domain.User
import kotlin.reflect.KClass

class EntityAlreadyExistException(override val message: String) : RuntimeException() {
    companion object {
        fun existsNickname(clazz: KClass<User>, nickname: String): Nothing =
            throw EntityAlreadyExistException(
                ErrorMessages.User.EXISTING_NICKNAME + "${clazz.simpleName} nickname : $nickname"
            )
    }
}
