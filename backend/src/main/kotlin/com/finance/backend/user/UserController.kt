package com.finance.backend.user

import com.finance.backend.Exceptions.TokenExpiredException
import com.finance.backend.user.Exceptions.InvalidUserException
import com.finance.backend.user.request.ChangeProfileDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/user")
class UserController (private val userService: UserService) {

    @GetMapping("")
    fun getUserInfo(@RequestHeader("access_token") accessToken : String) : ResponseEntity<Any?> {
        return try{
            ResponseEntity.status(200).body(userService.getUserInfo(accessToken))
        } catch (e : TokenExpiredException) {
            ResponseEntity.status(403).body("Token Expired")
        } catch (e : InvalidUserException) {
            ResponseEntity.status(409).body("Cannot Found User")
        } catch (e : Exception) {
            println(e)
            ResponseEntity.status(500).body("Internal Server Error")
        }
    }

    @PostMapping("/profile")
    fun changeProfile(@RequestHeader("access_token") accessToken : String, @RequestBody changeProfileDto : ChangeProfileDto) : ResponseEntity<Any?> {
        return try{
            ResponseEntity.status(200).body(userService.changeProfile(accessToken, changeProfileDto.profile_no))
        } catch (e : TokenExpiredException) {
            ResponseEntity.status(403).body("Token Expired")
        } catch (e : InvalidUserException) {
            ResponseEntity.status(409).body("Cannot Found User")
        } catch (e : Exception) {
            ResponseEntity.status(500).body("Internal Server Error")
        }
    }
}