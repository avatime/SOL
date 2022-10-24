package com.finance.backend.point

import com.finance.backend.auth.Exceptions.TokenExpiredException
import com.finance.backend.point.request.PointDto
import com.finance.backend.user.Exceptions.InvalidUserException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/point")
class PointController(private val pointService: PointService) {

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
            ResponseEntity.status(500).body("Internal Server Error")
        }
    }

    @PostMapping("")
    fun pointToCash(@RequestHeader("access_token") accessToken : String, @RequestBody pointDto: PointDto) : ResponseEntity<Any> {
        return try{
            ResponseEntity.status(200).body("")
        } catch (e : TokenExpiredException) {
            ResponseEntity.status(403).body("Token Expired")
        } catch (e : InvalidUserException) {
            ResponseEntity.status(409).body("Cannot Found User")
        } catch (e : Exception) {
            ResponseEntity.status(500).body("Internal Server Error")
        }
    }

}