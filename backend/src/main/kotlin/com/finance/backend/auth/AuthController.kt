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
        return try{
            ResponseEntity.status(201).body(userService.saveUser(userDto))
        } catch (e : DuplicatedUserException) {
            ResponseEntity.status(409).body(e.message)
        } catch (e : Exception) {
            ResponseEntity.status(500).body("Internal Server Error")
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody loginDto : LoginDto) : ResponseEntity<Any?> {
        return try {
            ResponseEntity.status(200).body(userService.login(loginDto))
        } catch (e : TokenExpiredException) {
            ResponseEntity.status(403).body("Token Expired")
        }catch (e : InvalidUserException) {
            ResponseEntity.status(404).body(e.message)
        } catch (e : InvalidPasswordException) {
            ResponseEntity.status(401).body("Invalid Password")
        } catch (e : Exception) {
            ResponseEntity.status(500).body("Internal Server Error")
        }
    }

    @PostMapping("/logout")
    fun logout(@RequestHeader accessToken: String) : ResponseEntity<Any?> {
        return try {
            userService.logout(accessToken)
            ResponseEntity.status(200).body("Success")
        } catch (e : TokenExpiredException) {
            ResponseEntity.status(403).body("Token Expired")
        } catch (e : Exception) {
            ResponseEntity.status(500).body("Internal Server Error")
        }
    }

    @PostMapping("/refresh")
    fun refresh(@RequestHeader refreshToken: String) : ResponseEntity<Any?> {
        return try{
            ResponseEntity.status(200).body(userService.refresh(refreshToken))
        } catch (e : TokenExpiredException) {
            ResponseEntity.status(403).body("Token Expired")
        } catch (e : Exception) {
            ResponseEntity.status(500).body("Internal Server Error")
        }
    }
}