package com.devfloor.hongit.api.user.application

import com.devfloor.hongit.api.common.exception.EntityNotFoundException
import com.devfloor.hongit.api.common.exception.ErrorMessages
import com.devfloor.hongit.api.security.auth.token.JwtTokenProvider
import com.devfloor.hongit.api.security.web.AuthorizationType
import com.devfloor.hongit.api.security.web.exception.AuthenticationException
import com.devfloor.hongit.api.user.application.request.LoginRequest
import com.devfloor.hongit.api.user.application.request.SignUpRequest
import com.devfloor.hongit.api.user.application.request.UserModifyRequest
import com.devfloor.hongit.api.user.application.response.ProfileResponse
import com.devfloor.hongit.api.user.application.response.TokenResponse
import com.devfloor.hongit.client.aws.s3.domain.S3DirectoryType
import com.devfloor.hongit.client.aws.s3.domain.spec.AwsS3Client
import com.devfloor.hongit.core.common.config.Slf4j.Companion.log
import com.devfloor.hongit.core.user.domain.User
import com.devfloor.hongit.core.user.domain.UserRepository
import com.devfloor.hongit.core.user.domain.UserType
import com.devfloor.hongit.core.user.domain.findByNicknameOrNull
import com.devfloor.hongit.core.user.domain.findByUsernameOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.util.Assert
import org.springframework.web.multipart.MultipartFile
import javax.transaction.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val awsS3Client: AwsS3Client,
) {
    fun signUp(request: SignUpRequest): Long {
        validateJoinInfo(request)

        return request.toUser()
            .apply { encodePassword(passwordEncoder) }
            .let { userRepository.save(it) }
            .also { log.info("[AuthService.join] 회원가입 완료 - UserId: ${it.id}") }
            .id
    }

    private fun validateJoinInfo(request: SignUpRequest) {
        Assert.isTrue(request.verifyInfo(), ErrorMessages.User.INVALID_REQUEST_INFO)
        Assert.isTrue(request.verifyPassword(), ErrorMessages.User.PASSWORD_VERIFICATION_MISMATCH)
        Assert.isTrue(userRepository.existsByUsername(request.username).not(), ErrorMessages.User.EXISTING_USERNAME)
        Assert.isTrue(userRepository.existsByNickname(request.nickname).not(), ErrorMessages.User.EXISTING_NICKNAME)
        Assert.isTrue(userRepository.existsByClassOf(request.classOf).not(), ErrorMessages.User.EXISTING_CLASS_OF)

        log.info("[AuthService.join] 회원가입 정보 유효성 검증 완료")
    }

    fun showByNickname(nickname: String): ProfileResponse = userRepository.findByNicknameOrNull(nickname)
        ?.let { ProfileResponse(it) }
        ?: EntityNotFoundException.notExistsField(User::class, "nickname", nickname)

    fun login(request: LoginRequest): TokenResponse {
        val user = userRepository.findByUsernameOrNull(request.username)
            ?: EntityNotFoundException.notExistsField(User::class, "username", request.username)

        if (!user.matchesPassword(passwordEncoder, request.password)) throw AuthenticationException("비밀번호가 일치하지 않습니다.")

        return TokenResponse(jwtTokenProvider.createToken(request.username), AuthorizationType.BEARER)
    }

    @Transactional
    fun modifyUser(loginUser: User, request: UserModifyRequest) {
        userRepository.findByNicknameOrNull(loginUser.nickname)
            ?.apply {
                if (validateNickname(loginUser.nickname, request.nickname)) {
                    modifyUser(
                        request.nickname,
                        UserType.valueOf(request.userType),
                        uploadImage(request.image),
                        request.github,
                        request.blog,
                        request.description
                    )
                }
            }
            ?: IllegalArgumentException(ErrorMessages.User.NOT_EXISTING_USERNAME)
    }

    private fun uploadImage(imagefile: MultipartFile?): String {
        return if (imagefile == null) {
            ""
        } else {
            awsS3Client.upload(imagefile, S3DirectoryType.PROFILE)
        }
    }

    private fun validateNickname(nickname: String, requestNickname: String): Boolean {
        if (!isExistNickname(requestNickname) && isNotEqualNickname(nickname, requestNickname)) {
            return true
        } else throw IllegalArgumentException(ErrorMessages.User.EXISTING_NICKNAME)
    }

    private fun isExistNickname(nickname: String): Boolean {
        return userRepository.existsByNickname(nickname)
    }

    private fun isNotEqualNickname(nickname: String, requestNickname: String): Boolean {
        return nickname != requestNickname
    }
}
