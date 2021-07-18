package com.devfloor.hongit.api

import com.devfloor.hongit.api.common.utils.SEOUL_TIME_ZONE
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import java.util.TimeZone
import javax.annotation.PostConstruct

@SpringBootApplication
@ComponentScan(basePackages = ["com.devfloor.hongit"])
class HongitApiApplication {
    @PostConstruct
    fun init() {
        TimeZone.setDefault(TimeZone.getTimeZone(SEOUL_TIME_ZONE))
    }
}

fun main() {
    runApplication<HongitApiApplication>()
}
