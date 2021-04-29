package com.devfloor.untitled.option.application

import com.devfloor.untitled.option.domain.OptionRepository

import com.devfloor.untitled.option.domain.Option
import com.devfloor.untitled.option.domain.OptionType

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OptionService(
    private val repository: OptionRepository
) {
    @Transactional(readOnly = true)
    fun show(options: List<String>): List<Option> {
        val optionList = mutableListOf<Option>()
        for(index in options.indices) {
            optionList.add(repository.findByType(showOptionType(options[index])))
        }
        return optionList
    }
    fun showOptionType(optionName: String): OptionType{
        return OptionType.valueOf(optionName) // 일치하는 enum constant가 없을 때 illegalArrgumentException 발생
    }


}
