package com.finance.backend.user

import com.finance.backend.common.util.JwtUtils
import lombok.RequiredArgsConstructor
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service("UserService")
@RequiredArgsConstructor
class UserServiceImpl (
        private val userRepository: UserRepository,
        private val passwordEncoder: BCryptPasswordEncoder,
//        private val authenticationManager: AuthenticationManager,
        private val jwtUtils: JwtUtils
        ) : UserService {
    override fun saveUser(userDto: UserDto) {
        userDto.password = passwordEncoder.encode(userDto.password)
        userRepository.save(userDto.toEntity())
    }

}