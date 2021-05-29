package com.devfloor.untitled.option.application

import com.devfloor.untitled.option.domain.Option
import com.devfloor.untitled.option.domain.OptionRepository
import com.devfloor.untitled.option.domain.OptionType
import org.springframework.stereotype.Service

@Service
class OptionService(
    private val repository: OptionRepository,
) {
    fun showAllByOptionType(optionTexts: List<String>): List<Option> {
        val optionTypes = optionTexts.map { OptionType.from(it) }
        return repository.findAllByTypeIn(optionTypes)
    }
}
