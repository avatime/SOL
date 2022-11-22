package com.finance.backend.notice

import com.finance.backend.Exceptions.InvalidUserException
import com.finance.backend.Exceptions.TokenExpiredException
import com.finance.backend.common.util.JwtUtils
import com.finance.backend.group.repository.PublicAccountMemberRepository
import com.finance.backend.user.User
import com.finance.backend.user.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
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

    override fun sendAlarm(token: String?, msg : String) {
        if(!token.isNullOrEmpty()) {
            val header: MultiValueMap<String, String> = LinkedMultiValueMap()
            val params: MutableMap<String, Any> = HashMap()
            val body: MutableMap<String, Any> = HashMap()
            params["to"] = token
            header["Authorization"] = "key=AAAAVpjOorU:APA91bFoPwu-4OeZGv7Jl-ms59jStQwWDlhYiq3NpaXIAnRrZL0aRSXOTKFXEzf_fvPapmBmAEf8ZCkxb7n1SmT_RwVh04mMMa0a2TR9xOnlZ16eXgNeOQsD7ibOVbLrIzvmQIlu1vis"
            body["body"] = msg
            params["notification"] = body
            val headers = HttpHeaders(header)
            val entity = HttpEntity<Map<String, Any>>(params, headers)

            val rt = RestTemplate()
            val response: ResponseEntity<String> = rt.exchange<String>(
                    "https://fcm.googleapis.com/fcm/send",
                    HttpMethod.POST,
                    entity,
                    String::class.java
            )
        }
    }

}