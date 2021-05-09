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
    fun showAllByOptionType(optionTexts: List<String>): List<Option> {
        val optionTypes = optionTexts.map { OptionType.from(it) }
        return repository.findAllByTypeIn(optionTypes)
    }
}
