package com.devfloor.hongit.api.common.exception

object ErrorMessages {
    object User {
        const val REQUEST_NOT_APPROVED = "승인되지 않은 회원가입 요청"
        const val EXISTING_USERNAME = "이미 존재하는 아이디"
        const val EXISTING_NICKNAME = "이미 존재하는 닉네임"
        const val PASSWORD_VERIFICATION_MISMATCH = "비밀번호 검증 불일치" // 비밀번호와 비밀번호확인이 일치하지 않음
    }
}
