package com.finance.backend.attendance

import com.finance.backend.attendance.response.AttendanceDao
import com.finance.backend.auth.Exceptions.TokenExpiredException
import com.finance.backend.user.Exceptions.InvalidUserException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/daily")
class DailyController(private val dailyService: DailyService) {

    @PostMapping("")
    fun check(@RequestHeader("access_token") accessToken : String) : ResponseEntity<Any?> {
        return try{
            ResponseEntity.status(200).body(dailyService.check(accessToken))
        } catch (e : TokenExpiredException) {
            ResponseEntity.status(403).body("Token Expired")
        } catch (e : InvalidUserException) {
            ResponseEntity.status(409).body("Cannot Found User")
        } catch (e : Exception) {
            ResponseEntity.status(500).body("Internal Server Error")
        }
    }

    @GetMapping("/{month}")
    fun getAttendance(@RequestHeader("access_token") accessToken : String,@PathVariable year: Int, @PathVariable month: Int) : ResponseEntity<Any?> {
        return try{
            ResponseEntity.status(200).body(dailyService.getAttendance(accessToken, year, month))
        } catch (e : TokenExpiredException) {
            ResponseEntity.status(403).body("Token Expired")
        } catch (e : InvalidUserException) {
            ResponseEntity.status(409).body("Cannot Found User")
        } catch (e : Exception) {
            ResponseEntity.status(500).body("Internal Server Error")
        }
    }

    @PostMapping("/walk")
    fun walk(@RequestHeader("access_token") accessToken : String) {

    }

    @GetMapping("/walk/{month}")
    fun getWalk(@RequestHeader("access_token") accessToken : String, @PathVariable month: Int) {

    }
}