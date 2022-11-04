package com.finance.backend.daily

import com.finance.backend.daily.request.WalkDto
import com.finance.backend.Exceptions.TokenExpiredException
import com.finance.backend.Exceptions.InvalidUserException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/daily")
class DailyController(private val dailyService: DailyService) {

    @PostMapping("")
    fun check(@RequestHeader("access_token") accessToken : String) : ResponseEntity<Any?> {
        return ResponseEntity.status(200).body(dailyService.check(accessToken))
    }

    @GetMapping("/{year}/{month}")
    fun getAttendance(@RequestHeader("access_token") accessToken : String,@PathVariable year: Int, @PathVariable month: Int) : ResponseEntity<Any?> {
        return ResponseEntity.status(200).body(dailyService.getAttendance(accessToken, year, month))
    }

    @PostMapping("/walk")
    fun walk(@RequestHeader("access_token") accessToken : String, @RequestBody walkDto: WalkDto) : ResponseEntity<Any?> {
        return ResponseEntity.status(200).body(dailyService.walk(accessToken, walkDto.walk))
    }

    @GetMapping("/walk/{year}/{month}")
    fun getWalk(@RequestHeader("access_token") accessToken : String, @PathVariable year: Int, @PathVariable month: Int): ResponseEntity<Any?> {
        return ResponseEntity.status(200).body(dailyService.getWalk(accessToken, year, month))
    }

    @GetMapping("/profiles")
    fun getAllProfiles(@RequestHeader("access_token") accessToken : String) : ResponseEntity<Any?> {
        return ResponseEntity.status(200).body(dailyService.getAllProfiles(accessToken))
    }

    @GetMapping("/test")
    fun test(@RequestHeader("access_token") accessToken : String) : ResponseEntity<Any?> {
        return ResponseEntity.status(200).body("Success")
    }
}