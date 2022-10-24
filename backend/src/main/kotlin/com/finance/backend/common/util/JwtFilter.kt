package com.finance.backend.common.util

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtFilter(private val jwtUtils: JwtUtils) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        println("sjafpodpjapfsf")
        // 헤더에 Authorization이 있다면 가져온다.
        val authorizationHeader: String? = request.getHeader("Authorization") ?: return filterChain.doFilter(request, response)
        // Bearer타입 토큰이 있을 때 가져온다.
        val token = authorizationHeader?.substring("Bearer ".length) ?: return filterChain.doFilter(request, response)

        println("dnjfnakjfndkjnfk")
        // 토큰 검증
        if (jwtUtils.validation(token)) {
            println("검증됨")
            // 토큰에서 userId 파싱
            val userId = jwtUtils.parseUserId(token)
            // userId로 AuthenticationToken 생성
//            val authentication: Authentication = jwtUtils.getAuthentication(userId)
            // 생성된 AuthenticationToken을 SecurityContext가 관리하도록 설정
//            SecurityContextHolder.getContext().authentication = authentication
            println("사용자 권한 부여")
        }

        filterChain.doFilter(request, response)
    }
}