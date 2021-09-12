package com.devfloor.hongit.api.common.exception

object ErrorMessages {
    object Common {
        const val ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE = "유효하지 않은 요청입니다."
    }

    object User {
        const val INVALID_REQUEST_INFO = "유효하지 않은 회원가입 요청 정보"
        const val PASSWORD_VERIFICATION_MISMATCH = "비밀번호 검증 불일치" // 비밀번호와 비밀번호확인이 일치하지 않음
        const val EXISTING_USERNAME = "이미 존재하는 아이디"
        const val EXISTING_NICKNAME = "이미 존재하는 닉네임"
        const val EXISTING_CLASS_OF = "이미 존재하는 학번"
    }
}
