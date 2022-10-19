package com.finance.backend.common.util

import com.finance.backend.auth.Token
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtils(
//        private val userDetailsService: UserDetailsServiceImpl
        ) {

    @Value("\${jwt.expiration}")
    val ACCESS_EXP_TIME: Long = 0

    @Value("\${jwt.refreshexpiration}")
    val REFRESH_EXP_TIME: Long = 0

    @Value("\${jwt.secret}")
    lateinit var JWT_SECRET: String

    @Value("\${jwt.refreshsecret}")
    lateinit var JWT_REFRESH_SECRET: String
    val SIGNATURE_ALG: SignatureAlgorithm = SignatureAlgorithm.HS256

    // 토큰생성
    fun createToken(userId: UUID, username: String, role: String): Token {
        val claims: Claims = Jwts.claims();
        claims["userId"] = userId
        claims["username"] = username
        claims["role"] = role

        val accessToken : String = Jwts.builder()
                .setClaims(claims)
                .setExpiration(Date(System.currentTimeMillis()+ ACCESS_EXP_TIME))
                .signWith(SIGNATURE_ALG, JWT_SECRET)
                .compact()
        val refreshToken : String = Jwts.builder()
                .setClaims(claims)
                .setExpiration(Date(System.currentTimeMillis()+ REFRESH_EXP_TIME))
                .signWith(SIGNATURE_ALG, JWT_REFRESH_SECRET)
                .compact()

        return Token(accessToken, refreshToken)
    }

    // 토큰검증
    fun validation(token: String) : Boolean {
        val claims: Claims = getAllClaims(token)
        val exp: Date = claims.expiration
        return exp.after(Date())
    }

    // refresh 토큰으로 부터 토큰 재발급
    fun refresh(token: String) : String {
        val claims: Claims = getAllClaims(token)
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(Date(System.currentTimeMillis()+ ACCESS_EXP_TIME))
                .signWith(SIGNATURE_ALG, JWT_SECRET)
                .compact()
    }

    // 토큰에서 userid 파싱
    fun parseUserId(token: String): String {
        val claims: Claims = getAllClaims(token)
        return claims["userId"] as String
    }

    // 토큰에서 username 파싱
    fun parseUsername(token: String): String {
        val claims: Claims = getAllClaims(token)
        return claims["username"] as String
    }

    // 토큰에서 role 파싱
    fun parseUserRole(token: String): String {
        val claims: Claims = getAllClaims(token)
        return claims["role"] as String
    }

    // username으로 Authentcation객체 생성
    fun getAuthentication(username: String): Authentication {
//        val userDetails: UserDetails = userDetailsService.loadUserByUsername(username)

        return UsernamePasswordAuthenticationToken(null, null, null)
//        return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
    }


    // 모든 Claims 조회
    private fun getAllClaims(token: String): Claims {
        return Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .body
    }
}