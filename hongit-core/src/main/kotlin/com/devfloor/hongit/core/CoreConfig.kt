package com.devfloor.hongit.core

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaAuditing
@EntityScan(basePackages = ["com.devfloor.hongit"])
@EnableJpaRepositories(basePackages = ["com.devfloor.hongit"])
class CoreConfig
