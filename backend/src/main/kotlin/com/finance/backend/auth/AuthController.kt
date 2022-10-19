package com.finance.backend.auth

import com.finance.backend.user.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController (private val userService: UserService) {

    @PostMapping("/signup")
    fun signup(@RequestBody userDto: SignupDto) = ResponseEntity.status(201).body(userService.saveUser(userDto))

    @PostMapping("/login")
    fun login(@RequestBody userDto: SignupDto) = ResponseEntity.status(200).body("")
}