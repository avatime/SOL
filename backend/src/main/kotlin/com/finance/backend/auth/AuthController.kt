package com.finance.backend.auth

import com.finance.backend.Exceptions.DuplicatedPhoneNumberException
import com.finance.backend.auth.request.LoginDto
import com.finance.backend.auth.request.ReLoginDto
import com.finance.backend.auth.request.SignupCheckDto
import com.finance.backend.auth.request.SignupDto
import com.finance.backend.user.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class AuthController (private val userService: UserService) {

    @PostMapping("/signup")
    fun signup(@RequestBody userDto: SignupDto) : ResponseEntity<Any?>{
        return ResponseEntity.status(200).body(userService.saveUser(userDto))
    }

    @PostMapping("/signup/check")
    fun signupCheck(@RequestBody userDto: SignupCheckDto) : ResponseEntity<Any?>{
        return ResponseEntity.status(200).body(userService.checkUser(userDto))
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
    fun logout(@RequestHeader("access_token") accessToken: String) : ResponseEntity<Any?> {
        userService.logout(accessToken)
        return ResponseEntity.status(200).body("Success")
    }

    @PostMapping("/refresh")
    fun refresh(@RequestHeader("refresh_token") refreshToken: String) : ResponseEntity<Any?> {
        return ResponseEntity.status(200).body(userService.refresh(refreshToken))
    }
}