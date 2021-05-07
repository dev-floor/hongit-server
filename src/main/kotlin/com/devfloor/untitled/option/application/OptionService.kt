package com.devfloor.untitled.option.application

import com.devfloor.untitled.option.domain.Option
import com.devfloor.untitled.option.domain.OptionRepository
import com.devfloor.untitled.option.domain.OptionType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OptionService(
    private val repository: OptionRepository
) {
    @Transactional(readOnly = true)
    fun showAllByOptionType(options: List<String>): List<Option> {
        return repository.findAllByTypeIn(options.map { OptionType.from(it) })
    }
}
