package com.devfloor.hongit.api.home.presentation

import com.devfloor.hongit.api.common.utils.BASE_API_URI
import com.devfloor.hongit.api.home.application.HomeBoardService
import com.devfloor.hongit.api.home.application.response.HomeResponse
import com.devfloor.hongit.api.home.presentation.HomeBoardController.Companion.HOME_API_URI
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = [HOME_API_URI])
class HomeBoardController(
    private val homeBoardService: HomeBoardService,
) {
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    fun showAll(): List<HomeResponse> = homeBoardService.showAll()

    companion object {
        const val HOME_API_URI = "$BASE_API_URI/home"
    }
}
