package com.finance.backend.point

import com.finance.backend.Exceptions.AccountNotSubToUserException
import com.finance.backend.Exceptions.TokenExpiredException
import com.finance.backend.point.request.RewardDto
import com.finance.backend.user.Exceptions.InvalidUserException
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
                "all" -> ResponseEntity.status(200).body(pointService.getAllPoint(accessToken))
                "in" -> ResponseEntity.status(200).body(pointService.getAccumulatedPoint(accessToken))
                "out" -> ResponseEntity.status(200).body(pointService.getUsedPoint(accessToken))
                else -> ResponseEntity.status(200).body(pointService.getAllPoint(accessToken))
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
        return try{
            ResponseEntity.status(200).body(pointService.changeToCash(accessToken, rewardDto))
        } catch (e : TokenExpiredException) {
            ResponseEntity.status(403).body("Token Expired")
        } catch (e : InvalidUserException) {
            ResponseEntity.status(409).body("Cannot Found User")
        } catch (e : AccountNotSubToUserException) {
            ResponseEntity.status(404).body(e.message)
        } catch (e : NullPointerException) {
            ResponseEntity.status(402).body("설정된 대표 계좌가 없습니다.")
        } catch (e : Exception) {
            ResponseEntity.status(500).body("Internal Server Error")
        }
    }

}