package com.finance.backend.user

import com.finance.backend.auth.LoginDTO
import com.finance.backend.auth.Token
import com.finance.backend.auth.SignupDto
import com.finance.backend.common.util.JwtUtils
import lombok.RequiredArgsConstructor
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service("UserService")
@RequiredArgsConstructor
class UserServiceImpl (
        private val userRepository: UserRepository,
        private val passwordEncoder: BCryptPasswordEncoder,
//        private val authenticationManager: AuthenticationManager,
        private val jwtUtils: JwtUtils
        ) : UserService {
    override fun saveUser(signupDto: SignupDto) : User {
        signupDto.password = passwordEncoder.encode(signupDto.password)
        var user : User = signupDto.toEntity()
        // 토큰 발급
        user = userRepository.save(user)
        val token : Token = jwtUtils.createToken(user.id, user.name, signupDto.type)
        user.accessToken(token.accessToken)
        user.refreshToken(token.refreshToken)
        return userRepository.save(user)
    }

    override fun login(loginDto: LoginDTO) {
//        var user : User = userRepository.findBy
    }

}