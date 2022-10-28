package com.finance.backend.user

import com.finance.backend.Exceptions.*
import com.finance.backend.auth.*
import com.finance.backend.auth.request.LoginDto
import com.finance.backend.auth.request.ReLoginDto
import com.finance.backend.auth.request.SignupDto
import com.finance.backend.auth.response.LoginDao
import com.finance.backend.common.util.JwtUtils
import com.finance.backend.profile.Profile
import com.finance.backend.profile.ProfileRepository
import com.finance.backend.user.response.UserDao
import lombok.RequiredArgsConstructor
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*

@Service("UserService")
@RequiredArgsConstructor
class UserServiceImpl (
        private val userRepository: UserRepository,
        private val profileRepository: ProfileRepository,
        private val passwordEncoder: BCryptPasswordEncoder,
//        private val authenticationManager: AuthenticationManager,
        private val jwtUtils: JwtUtils
        ) : UserService {
    override fun saveUser(signupDto: SignupDto) : LoginDao {
        if(userRepository.existsByNameAndPhoneAndBirth(signupDto.username, signupDto.phone, SimpleDateFormat("yyyy-MM-dd").parse(signupDto.birth))) throw DuplicatedUserException()
        else {
            signupDto.password = passwordEncoder.encode(signupDto.password)
            var user : User? = userRepository.findByPhone(signupDto.phone)
            if(user == null) user = signupDto.toEntity()
            else if(user.type != "비회원") throw DuplicatedPhoneNumberException()
            else user.toMember(signupDto.password, SimpleDateFormat("yyyy-MM-dd").parse(signupDto.birth), signupDto.sex)
            // 토큰 발급
            user = userRepository.save(user)
            try {
                val token : Token = jwtUtils.createToken(user.id, user.name, signupDto.type)
                user.accessToken(token.accessToken)
                user.refreshToken(token.refreshToken)
                return userRepository.saveAndFlush(user).toLoginEntity()
            } catch (e : Exception) {
                throw Exception()
            }
        }
    }

    override fun checkUser(signupDto: SignupDto) {
        if(userRepository.existsByNameAndPhoneAndBirth(signupDto.username, signupDto.phone, SimpleDateFormat("yyyy-MM-dd").parse(signupDto.birth))) throw DuplicatedUserException()
        else {
            var user : User? = userRepository.findByPhone(signupDto.phone)
            if(user == null) user = signupDto.toEntity()
            else if(user.type != "비회원") throw DuplicatedPhoneNumberException()
        }
    }

    override fun login(loginDto: LoginDto) : LoginDao? {
        if(try {jwtUtils.validation(loginDto.refreshToken)} catch (e: Exception) {throw TokenExpiredException()
                }){
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(loginDto.refreshToken))
            val user : User = userRepository.findById(userId).orElseGet(null) ?: throw InvalidUserException()
            if(passwordEncoder.matches(loginDto.password, user.password)) {
                user.accessToken(jwtUtils.refresh(loginDto.refreshToken))
                return userRepository.save(user).toLoginEntity()
            } else throw InvalidPasswordException()
        } else throw Exception()
    }

    override fun reLogin(reLoginDto: ReLoginDto): LoginDao? {
        val user : User = userRepository.findByPhone(reLoginDto.phone)?:throw InvalidUserException()
        if(passwordEncoder.matches(reLoginDto.password, user.password)) {
            val token : Token = jwtUtils.createToken(user.id, user.name, user.type)
            user.accessToken(token.accessToken)
            user.refreshToken(token.refreshToken)
            return userRepository.save(user).toLoginEntity()
        } else throw InvalidPasswordException()
    }

    override fun logout(token: String): Boolean {
        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException()
                }) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))
            val user : User = userRepository.findById(userId).orElseGet(null) ?: throw InvalidUserException()
            user.accessToken("")
            userRepository.save(user)
        }
        return false
    }

    override fun refresh(token: String) : String {
        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException()
                }) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))
            val user : User = userRepository.findById(userId).orElseGet(null) ?: throw InvalidUserException()
            user.accessToken(jwtUtils.refresh(token))
            userRepository.save(user)
            return user.accessToken
        }
        throw Exception()
    }

    override fun getUserInfo(token: String): UserDao {
        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException()
                }) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))
            val user : User = userRepository.findById(userId).orElse(null) ?: throw InvalidUserException()
            val profile : Profile = profileRepository.findByPfId(user.pfId) ?: throw NoProfileException()
            return UserDao(user.name, profile.pfName, profile.pfImg, user.point)
        }
        throw Exception()
    }

    override fun changeProfile(token: String, id: Long) {
        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException()
                }) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))
            val user : User = userRepository.findById(userId).orElseGet(null) ?: throw InvalidUserException()
            user.pfId(id)
            userRepository.save(user)
        }
    }

    override fun check(phone: String): Boolean {
        return !userRepository.existsByPhoneAndType(phone, "회원")
    }
}