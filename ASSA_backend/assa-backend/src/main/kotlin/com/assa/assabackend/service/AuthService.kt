package com.assa.assabackend.service

import com.assa.assabackend.config.JwtTokenProvider
import com.assa.assabackend.dto.*
import com.assa.assabackend.entity.AppUser
import com.assa.assabackend.repository.UserRepository
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.concurrent.TimeUnit


@Service
@Transactional
class AuthService(
    private val redisTemplate: RedisTemplate<String, String>,
    private val mailSender: JavaMailSender,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider
) {

    companion object { // java static 같은놈
        private const val EMAIL_VERIFICATION_PREFIX = "email_verification:"
        private const val EMAIL_ATTEMPTS_PREFIX = "email_attempts:"
        private const val CODE_LENGTH = 6
        private const val EXPIRATION_MINUTES = 10L
        private const val MAX_ATTEMPTS = 5

        private val logger = LoggerFactory.getLogger(AuthService::class.java)
    }

    private fun generationVerificationCode() : String {
        return (100000..999999).random().toString()
    }

    private fun sendMail(email: String, code: String){
        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true, "UTF-8")

        helper.setTo(email)
        helper.setSubject(" ASSA 회원가입 인증번호 입니다. ")
        helper.setText(buildEmailContent(code), true)

        mailSender.send(message)
    }


    fun sendVerificationCode(email : String): ApiResponse<VerificationStatusResponse> {
        try{
            if(userRepository.existsByEmail(email)){ // 1. 이메일 가입여부 검증
                return ApiResponse(false, "이미 가입된 이메일입니다.")
            }

            // 시도 횟수 검사하깅
            val attemptsKey = EMAIL_ATTEMPTS_PREFIX + email
            val attempts = redisTemplate.opsForValue().get(attemptsKey)?.toIntOrNull() ?: 0

            if (attempts >= MAX_ATTEMPTS) {
                return ApiResponse(false, "인증 시도 횟수를 초과했습니다. 잠시 후 다시 시도해주세요")
            }

            // 인증 번호 생성
            val verificationCode = generationVerificationCode()

            // 레디스에 저장하기.
            val codeKey = EMAIL_VERIFICATION_PREFIX + email
            redisTemplate.opsForValue().set(
                codeKey,
                verificationCode,
                Duration.ofMinutes(EXPIRATION_MINUTES)
            ) // 키, 밸류, 유효시간

            //  시도 횟수 증가
            redisTemplate.opsForValue().set(
                attemptsKey,
                (attempts + 1).toString(),
                Duration.ofHours(1) // 1시간 후 리셋..인데 너무 긴가?
            )

            // 인증 번호 발송
            sendMail(email, verificationCode)


            val response = VerificationStatusResponse(
                remainingTime = EXPIRATION_MINUTES * 60,
                attemptsLeft = MAX_ATTEMPTS - attempts - 1
            )

            logger.info("Verification code sent to: $email")
            return ApiResponse(true, "인증번호가 발송되었습니다", response)

        } catch (e: Exception){
            logger.error("Verification code sent error to: $email", e)
            return ApiResponse(false, "인증번호 발송에 실패했습니다.")
        }
    }

    fun verifyCode(email: String, inputCode: String): ApiResponse<Nothing>{
        val codeKey: String = EMAIL_VERIFICATION_PREFIX + email
        val storedCode = redisTemplate.opsForValue().get(codeKey)

        return if(storedCode != null && storedCode == inputCode){
                redisTemplate.delete(codeKey)
                redisTemplate.delete(EMAIL_ATTEMPTS_PREFIX + email)

                redisTemplate.opsForValue().set(
                    "email_verified:$email",
                    "true",
                    Duration.ofMinutes(30L)
                )
                logger.info("Email verification successful for: $email")
                ApiResponse(true, "이메일 인증이 완료되었습니다")
            }
            else{
                logger.warn("Email verification failed for: $email")
                ApiResponse(false, "인증번호가 올바르지 않습니다")
            }
    }

    fun getVerificationStatus(email: String) : ApiResponse<VerificationStatusResponse> {
        val codeKey = EMAIL_VERIFICATION_PREFIX + email
        val attemptsKey = EMAIL_ATTEMPTS_PREFIX + email

        val remainingTime = redisTemplate.getExpire(codeKey, TimeUnit.SECONDS)
        val attempts = redisTemplate.opsForValue().get(attemptsKey)?.toIntOrNull() ?: 0

        val response = VerificationStatusResponse(
            remainingTime = if (remainingTime > 0) remainingTime else 0,
            attemptsLeft = maxOf(0, MAX_ATTEMPTS - attempts)
        )

        return ApiResponse(true, "인증 상태 조회 성공", response)
    }

    /**
     * 회원가입
     */
    fun signup(request: SignupRequest): ApiResponse<Nothing> {
        // 이메일 인증 완료 여부 확인
        val verifiedKey = "email_verified:${request.email}"
        val isVerified = redisTemplate.opsForValue().get(verifiedKey)

        if (isVerified != "true") {
            return ApiResponse(false, "이메일 인증을 먼저 완료해주세요")
        }


        // 2. 중복 이메일 확인
        if (userRepository.existsByEmail(request.email)) {
            return ApiResponse(false, "이미 가입된 이메일입니다")
        }
        /**
         * user_id serial v
         * region_metro_id v
         * region_district_id
         * name v
         * email v
         * phone_id  v
         * profile_path v
         * is_deleted
         * deleted_time
         * created_time
         * password v
         * email_verified
         */
        val user = AppUser(
            email = request.email,
            regionMetroId = request.region_metro_id,
            password = passwordEncoder.encode(request.password),
//            phoneId = request.phone_id,
            phoneId = null,
            name = request.name,
            profilePath = request.profile_path,
            emailVerified = true,
        )

        userRepository.save(user)

        // 4. 인증 완료 상태 삭제
        redisTemplate.delete(verifiedKey)

        logger.info("User signup successful: ${request.email}")
        return ApiResponse(true, "회원가입이 완료되었습니다")
    }

    /**
     * 로그인
     */
    fun login(request: LoginRequest): TokenResponse {
        logger.info("email :: " + request.email)
        val user = userRepository.findByEmail(request.email)
            ?: throw BadCredentialsException("Invalid email or pwd")
        logger.info(request.password +" :: " + user.password)

        if(!passwordEncoder.matches(request.password, user.password)){
            throw BadCredentialsException("Invalid email or pwd")
        }

        val accessToken = jwtTokenProvider.generateAccessToken(user.userId)
        val refreshToken = jwtTokenProvider.generateRefreshToken(user.userId)

        return TokenResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
            expiresIn = 1800
        )
    }

    private fun buildEmailContent(code: String): String {
        return """
            <html>
            <body style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;">
                <div style="background-color: #f8f9fa; padding: 20px; text-align: center;">
                    <h2 style="color: #333;">이메일 인증</h2>
                    <p style="font-size: 16px; color: #666;">
                        안녕하세요! 아래 인증번호를 입력해주세요.
                    </p>
                    <div style="background-color: white; padding: 20px; margin: 20px 0; border-radius: 8px; border: 2px dashed #007bff;">
                        <h1 style="color: #007bff; font-size: 36px; margin: 0; letter-spacing: 5px;">$code</h1>
                    </div>
                    <p style="font-size: 14px; color: #999;">
                        이 인증번호는 ${EXPIRATION_MINUTES}분간 유효합니다.
                    </p>
                </div>
            </body>
            </html>
        """.trimIndent()
    }
}