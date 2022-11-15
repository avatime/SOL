package com.finance.backend.point

import com.finance.backend.Exceptions.AccountNotSubToUserException
import com.finance.backend.Exceptions.TokenExpiredException
import com.finance.backend.point.request.RewardDto
import com.finance.backend.Exceptions.InvalidUserException
import com.finance.backend.point.request.GetRewardDto
import com.finance.backend.user.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/point")
class RewardController(private val pointService: RewardService, private val userRepository: UserRepository) {
    @GetMapping("/{filter}")
    fun getPoints(@RequestHeader("access_token") accessToken : String, @PathVariable filter: String) : ResponseEntity<Any?> {
        return try{
            when (filter) {
                "all" -> ResponseEntity.status(200).body(pointService.getAllPoint(accessToken).reversed())
                "in" -> ResponseEntity.status(200).body(pointService.getAccumulatedPoint(accessToken).reversed())
                "out" -> ResponseEntity.status(200).body(pointService.getUsedPoint(accessToken).reversed())
                else -> ResponseEntity.status(200).body(pointService.getAllPoint(accessToken).reversed())
            }
        } catch (e : TokenExpiredException) {
            ResponseEntity.status(403).body("Token Expired")
        } catch (e : InvalidUserException) {
            ResponseEntity.status(409).body("Cannot Found User")
        } catch (e : Exception) {
            println(e)
            ResponseEntity.status(500).body("Internal Server Error")
        }
    }

    @PostMapping("")
    fun pointToCash(@RequestHeader("access_token") accessToken : String, @RequestBody rewardDto: RewardDto) : ResponseEntity<Any> {
        return ResponseEntity.status(200).body(pointService.changeToCash(accessToken, rewardDto))
    }

    @PostMapping("/easter")
    fun getPoint(@RequestHeader("access_token") accessToken : String, @RequestBody getRewardDto: GetRewardDto) : ResponseEntity<Any> {
        return ResponseEntity.status(200).body(pointService.getPoint(accessToken, getRewardDto))
    }

}