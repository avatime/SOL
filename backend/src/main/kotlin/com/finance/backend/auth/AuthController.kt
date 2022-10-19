package com.finance.backend.auth

import com.finance.backend.auth.Exceptions.DuplicatedUserException
import com.finance.backend.auth.Exceptions.InvalidPasswordException
import com.finance.backend.user.Exceptions.InvalidUserException
import com.finance.backend.auth.Exceptions.TokenExpiredException
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
        try{
            return ResponseEntity.status(201).body(userService.saveUser(userDto))
        } catch (e : DuplicatedUserException) {
            return ResponseEntity.status(409).body(e.message)
        } catch (e : Exception) {
            return ResponseEntity.status(500).body("Internal Server Error")
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody loginDto : LoginDto) : ResponseEntity<Any?> {
        try {
            return ResponseEntity.status(200).body(userService.login(loginDto))
        } catch (e : TokenExpiredException) {
            return ResponseEntity.status(403).body("Token Expired")
        }catch (e : InvalidUserException) {
            return ResponseEntity.status(404).body(e.message)
        } catch (e : InvalidPasswordException) {
            return ResponseEntity.status(401).body("Invalid Password")
        } catch (e : Exception) {
            return ResponseEntity.status(500).body("Internal Server Error")
        }
    }

    @PostMapping("/logout")
    fun logout(@RequestHeader token: String) : ResponseEntity<Any?> {
        try {
            userService.logout(token)
            return ResponseEntity.status(200).body("Success")
        } catch (e : Exception) {
            return ResponseEntity.status(500).body("Internal Server Error")
        }
    }
}