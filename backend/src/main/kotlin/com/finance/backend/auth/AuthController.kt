package com.finance.backend.auth

import com.finance.backend.user.UserDto
import com.finance.backend.user.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/api/v1/auth")
class AuthController (private val userService: UserService) {

    @PostMapping("/signup")
    fun signup(@RequestBody userDto: UserDto) = ResponseEntity.status(201).body(userService.saveUser(userDto))
}