package com.devfloor.hongit.api.support

import org.mockito.Mockito

object MockitoHelper {
    inline fun <reified T> any(): T = Mockito.any(T::class.java)
}
