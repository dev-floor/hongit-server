package com.devfloor.hongit.client.mail.support

import org.mockito.Mockito

object MockitoHelper {
    inline fun <reified T> any(): T = Mockito.any(T::class.java)
}
