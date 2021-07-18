package com.devfloor.hongit.core.option.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
interface OptionRepository : JpaRepository<Option, Long>
