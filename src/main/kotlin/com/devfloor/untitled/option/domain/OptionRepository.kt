package com.devfloor.untitled.option.domain

import org.springframework.data.jpa.repository.JpaRepository

interface OptionRepository : JpaRepository<Option, Long> {
    fun findAllByTypeIn(types: List<OptionType>): List<Option>
}
