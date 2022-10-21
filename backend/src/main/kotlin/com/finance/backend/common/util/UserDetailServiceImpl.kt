package com.finance.backend.common.util

import com.finance.backend.user.User
import com.finance.backend.user.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(userId: String): UserDetails {
        val user: User = userRepository.findById(userId).orElseGet(null) ?: throw UsernameNotFoundException("존재하지 않는 회원입니다.")

        return UserDetailsImpl(user)
    }
}