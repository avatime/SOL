package com.finance.backend.notice

import com.finance.backend.Exceptions.InvalidUserException
import com.finance.backend.Exceptions.TokenExpiredException
import com.finance.backend.common.util.JwtUtils
import com.finance.backend.group.repository.PublicAccountMemberRepository
import com.finance.backend.user.User
import com.finance.backend.user.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import java.util.*

@Service("NoticeService")
@RequiredArgsConstructor
class NoticeServiceImpl (
        private val jwtUtils: JwtUtils,
        private val userRepository: UserRepository,
        private val publicAccountMemberRepository: PublicAccountMemberRepository
        ) : NoticeService {

    override fun registNoticeToken(token: String, noticeRegistRequest: NoticeRegistRequest) {
        if(try {jwtUtils.validation(token)} catch (e: Exception) {throw TokenExpiredException()
                }) {
            val userId : UUID = UUID.fromString(jwtUtils.parseUserId(token))
            val user : User = userRepository.findById(userId).orElseGet(null) ?: throw InvalidUserException()
            user.registNoticeToken(noticeRegistRequest.token)
            userRepository.save(user)
        } else throw Exception()
    }


}