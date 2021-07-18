package com.devfloor.hongit.api.common.application.response

data class ErrorResponse(
    val errorClass: String,
    val errorMessage: String,
)
