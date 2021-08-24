package com.devfloor.hongit.client.mail.common.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "hongit")
class MailAuthProperties(
    val authUrl: String,
)
