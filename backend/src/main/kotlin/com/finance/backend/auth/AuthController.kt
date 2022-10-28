package com.finance.backend.auth

import com.finance.backend.Exceptions.DuplicatedUserException
import com.finance.backend.Exceptions.InvalidPasswordException
import com.finance.backend.Exceptions.InvalidUserException
import com.finance.backend.Exceptions.TokenExpiredException
import com.finance.backend.auth.request.LoginDto
import com.finance.backend.auth.request.ReLoginDto
import com.finance.backend.auth.request.SignupDto
import com.finance.backend.user.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController (private val userService: UserService) {

    @PostMapping("/signup")
    fun signup(@RequestBody userDto: SignupDto) : ResponseEntity<Any?>{
        return ResponseEntity.status(200).body(userService.saveUser(userDto))
    }

    @PostMapping("/signup/check")
    fun signupCheck(@RequestBody userDto: SignupDto) : ResponseEntity<Any?>{
        return ResponseEntity.status(200).body(userService.saveUser(userDto))
    }

    @PostMapping("/login")
    fun login(@RequestBody loginDto : LoginDto) : ResponseEntity<Any?> {
        return ResponseEntity.status(200).body(userService.login(loginDto))
    }

    @PostMapping("/relogin")
    fun reLogin(@RequestBody reLoginDto : ReLoginDto) : ResponseEntity<Any?> {
        return ResponseEntity.status(200).body(userService.reLogin(reLoginDto))
    }

    @PostMapping("/logout")
    fun logout(@RequestHeader accessToken: String) : ResponseEntity<Any?> {
        userService.logout(accessToken)
        return ResponseEntity.status(200).body("Success")
    }

    @PostMapping("/refresh")
    fun refresh(@RequestHeader refreshToken: String) : ResponseEntity<Any?> {
        return ResponseEntity.status(200).body(userService.refresh(refreshToken))
    }
}