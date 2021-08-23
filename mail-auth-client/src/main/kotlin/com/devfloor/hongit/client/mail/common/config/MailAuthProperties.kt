package com.devfloor.hongit.client.mail.common.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "hongit")
class MailAuthProperties(
    val authApiUrl: String,
    val authUrl: String,
)
