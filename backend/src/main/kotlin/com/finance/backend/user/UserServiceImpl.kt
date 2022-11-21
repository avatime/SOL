package com.finance.backend.user

import com.finance.backend.Exceptions.*
import com.finance.backend.auth.*
import com.finance.backend.auth.request.LoginDto
import com.finance.backend.auth.request.ReLoginDto
import com.finance.backend.auth.request.SignupCheckDto
import com.finance.backend.auth.request.SignupDto
import com.finance.backend.auth.response.LoginDao
import com.finance.backend.bookmark.Bookmark
import com.finance.backend.common.util.JwtUtils
import com.finance.backend.group.entity.PublicAccountMember
import com.finance.backend.group.repository.PublicAccountMemberRepository
import com.finance.backend.group.repository.PublicAccountRepository
import com.finance.backend.profile.Profile
import com.finance.backend.profile.ProfileRepository
import com.finance.backend.user.response.UserDao
import lombok.RequiredArgsConstructor
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

@Service("UserService")
@RequiredArgsConstructor
class UserServiceImpl (
        private val userRepository: UserRepository,
        private val profileRepository: ProfileRepository,
        private val passwordEncoder: BCryptPasswordEncoder,
        private val publicAccountMemberRepository: PublicAccountMemberRepository,
//        private val authenticationManager: AuthenticationManager,
        private val jwtUtils: JwtUtils
        ) : UserService {
    override fun saveUser(signupDto: SignupDto) : LoginDao {
            signupDto.password = passwordEncoder.encode(signupDto.password)
            var user : User? = userRepository.findByPhone(signupDto.phone)
            if(user == null) user = signupDto.toEntity()
            else if(user.type != "비회원") throw DuplicatedPhoneNumberException()
            else if(user.type == "비회원") {
                val group : List<PublicAccountMember> = publicAccountMemberRepository.findAllByUser(user) ?: emptyList()
                for(member : PublicAccountMember in group) {
                    member.type = "회원"
                    publicAccountMemberRepository.save(member)
                }
                user.toMember(signupDto.password, SimpleDateFormat("yyyy-MM-dd").parse(signupDto.birth), signupDto.sex)
            }
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

    override fun checkUser(signupDto: SignupCheckDto){
        var user : User? = userRepository.findByPhone(signupDto.phone) ?: return
        val formatter = SimpleDateFormat("YYYY-MM-dd")
        val birth = formatter.parse(signupDto.birth)
        if(user?.name == signupDto.username && formatter.format(user?.birth) == signupDto.birth) throw DuplicatedUserException()
        else if(user?.type == "회원") throw DuplicatedPhoneNumberException()
    }

    override fun login(loginDto: LoginDto) : LoginDao? {
        if(try {jwtUtils.validation(loginDto.refreshToken)} catch (e: Exception) { throw TokenExpiredException() }){
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

    override fun checkAccount(token: String): Boolean {
        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException()}) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))
            val user = userRepository.findById(userId).orElse(null)
            return user.account.isNotEmpty()
        }
        throw Exception()
    }

    override fun putAccount(token: String, acNo: String) {
        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException()}) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))
            val user = userRepository.findById(userId).orElse(null)
            user.changeAccount(acNo)
            userRepository.save(user)
        }
    }
}