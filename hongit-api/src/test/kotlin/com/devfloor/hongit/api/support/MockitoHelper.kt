package com.devfloor.hongit.api.support

import org.mockito.Mockito

object MockitoHelper {
    fun <T> any(type: Class<T>): T = Mockito.any(type)
}
