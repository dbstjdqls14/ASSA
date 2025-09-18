package com.assa.assabackend.service

import com.assa.assabackend.entity.AppUser
import com.assa.assabackend.repository.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    @Transactional(readOnly = true)
    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("사용자를 찾을 수 없습니다: $email")

        return CustomUserDetails(user)
    }
}

class CustomUserDetails(
    private val user: AppUser
) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority("ROLE_ROLE"))
    }

    override fun getPassword(): String = user.password
    override fun getUsername(): String = user.email
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = !user.isDeleted
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = user.emailVerified && !user.isDeleted

    fun getUser(): AppUser = user
}
