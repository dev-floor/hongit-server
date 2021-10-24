package com.devfloor.hongit.api.security.web

enum class AuthorizationType {
    BASIC,
    BEARER,
    ;

    fun toLowerCase(): String = this.name.lowercase()
}
