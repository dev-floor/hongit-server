package com.devfloor.hongit.api.home.presentation

import com.devfloor.hongit.api.home.application.HomeBoardService
import com.devfloor.hongit.api.home.application.response.HomeResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeBoardController(
    private val homeBoardService: HomeBoardService,
) {
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    fun showAll(): List<HomeResponse> = homeBoardService.showAll()
}
