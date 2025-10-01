package com.assa.assabackend.config

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.security.SignatureException
import java.time.Duration
import java.util.*
import javax.crypto.SecretKey

/**
 * jdk 25부터 provider 안써도 되는 넘
 */
@Component
class JwtTokenProvider(
    private val redisTemplate: RedisTemplate<String, String>
) {

    @Value("\${jwt.secret}")
    private lateinit var jwtSecret: String

    @Value("\${jwt.expiration}")
    private var jwtRTExpiration: Long = 0

    @Value("\${jwt.access-token-expiration}")
    private var jwtATExpiration: Long = 0

    private val logger = LoggerFactory.getLogger(JwtTokenProvider::class.java)

    private val key: SecretKey by lazy {
        Keys.hmacShaKeyFor(jwtSecret.toByteArray())
    }

    fun generateAccessToken(userId: Long): String {
        val now = Date()
        val expiryDate = Date(now.time + jwtATExpiration)

        return Jwts.builder()
            .setSubject(userId.toString())
            .claim("type","access")
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(key)
            .compact()
    }

    fun generateRefreshToken(userId: Long): String {
        val now = Date()
        val expiryDate = Date(now.time + jwtRTExpiration)

        val refreshToken = Jwts.builder()
            .setSubject(userId.toString())
            .claim("type","refresh")
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(key)
            .compact()

        redisTemplate.opsForValue().set(
            "refresh_token:$userId",
            refreshToken,
            Duration.ofMillis(jwtRTExpiration)
        )

        return refreshToken
    }

    fun getUserIdFromToken(token: String): Long {
        return getClaims(token).subject.toLong()
    }

    fun validateToken(token: String): Boolean {
        return try {
            logger.info("=================123123 ====== + " + token)
            val claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)

            val isExpired = claims.body.expiration.before(Date())
            if (isExpired) {
                logger.warn("토큰이 만료되었습니다")
                return false
            }

            true

        } catch (e: SecurityException) {
            logger.error("JWT 서명이 유효하지 않습니다: ${e.message}")
            false
        } catch (e: MalformedJwtException) {
            logger.error("JWT 형식이 잘못되었습니다: ${e.message}")
            false
        } catch (e: ExpiredJwtException) {
            logger.error("JWT 토큰이 만료되었습니다: ${e.message}")
            logger.error("만료 시간: ${e.claims.expiration}")
            logger.error("현재 시간: ${Date()}")
            false
        } catch (e: UnsupportedJwtException) {
            logger.error("지원하지 않는 JWT 토큰입니다: ${e.message}")
            false
        } catch (e: IllegalArgumentException) {
            logger.error("JWT 토큰이 비어있습니다: ${e.message}")
            false
        } catch (e: Exception) {
            logger.error("JWT 토큰 검증 중 예상치 못한 오류: ${e.message}", e)
            false
        }
    }


    fun validateRefreshToken(token: String, userId: Long): Boolean {
        return try {
            if(!validateToken(token)) return false
            logger.info("================" + token)
            val storedToken = redisTemplate.opsForValue().get("refresh_token:$userId")
            storedToken == token
        } catch(e: Exception){
            logger.error("Refresh token validation error: ${e.message}")
            false
        }
    }

    fun invalidateToken(userId: Long) {
        redisTemplate.delete("refresh_token:$userId")
    }

    private fun getClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
    }

}