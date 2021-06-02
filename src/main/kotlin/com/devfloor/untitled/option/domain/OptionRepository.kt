package com.devfloor.untitled.option.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

/**
 * 옵션을 관리하는 repository
 */
@Transactional(readOnly = true)
interface OptionRepository : JpaRepository<Option, Long> {
    fun findAllByIdIn(id: Collection<Long>): List<Option>
}
