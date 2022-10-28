package com.finance.backend.user

import com.finance.backend.Exceptions.TokenExpiredException
import com.finance.backend.Exceptions.InvalidUserException
import com.finance.backend.Exceptions.NoProfileException
import com.finance.backend.user.request.ChangeProfileDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/user")
class UserController (private val userService: UserService) {

    @GetMapping("")
    fun getUserInfo(@RequestHeader("access_token") accessToken : String) : ResponseEntity<Any?> {
        return ResponseEntity.status(200).body(userService.getUserInfo(accessToken))
    }

    @PostMapping("/profile")
    fun changeProfile(@RequestHeader("access_token") accessToken : String, @RequestBody changeProfileDto : ChangeProfileDto) : ResponseEntity<Any?> {
        return ResponseEntity.status(200).body(userService.changeProfile(accessToken, changeProfileDto.profile_no))
    }
}