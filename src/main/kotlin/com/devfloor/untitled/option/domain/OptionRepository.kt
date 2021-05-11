package com.devfloor.untitled.option.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@Transactional
interface OptionRepository : JpaRepository<Option, Long> {

    @Transactional(readOnly = true)
    fun findAllByTypeIn(types: List<OptionType>): List<Option>
}
