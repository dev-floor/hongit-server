package com.devfloor.untitled.common.presentation

import com.devfloor.untitled.common.config.Slf4j
import com.devfloor.untitled.common.presentation.BaseController.Companion.BASE_API_URI
import org.springframework.web.bind.annotation.RestController

/**
 * controller 관련 공통 정보를 관리하는 객체
 *
 * @property BASE_API_URI API 시작 주소
 */
@Slf4j
@RestController
abstract class BaseController {
    companion object {
        const val BASE_API_URI = "/api"
    }
}
