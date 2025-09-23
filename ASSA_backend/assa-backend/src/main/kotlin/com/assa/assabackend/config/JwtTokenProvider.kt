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
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token)
        } catch (e: SignatureException) {
            logger.error("Invalid JWT signature")
        } catch (ex: MalformedJwtException) {
            logger.error("Invalid JWT token")
        } catch (ex: ExpiredJwtException) {
            logger.error("Expired JWT token")
        } catch (ex: UnsupportedJwtException) {
            logger.error("Unsupported JWT token")
        } catch (ex: IllegalArgumentException) {
            logger.error("JWT claims string is empty")
        }
        return false
    }

    fun validateRefreshToken(token: String, userId: Long): Boolean {
        return try {
            if(!validateToken(token)) return false

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
            .setSigningKey(jwtSecret)
            .build()
            .parseClaimsJws(token)
            .body
    }

}