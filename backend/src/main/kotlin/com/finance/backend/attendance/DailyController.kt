package com.finance.backend.attendance

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/daily")
class DailyController(private val dailyService: DailyService) {

    @PostMapping("")
    fun check() {

    }

    @GetMapping("/{month}")
    fun getAttendance(@PathVariable month: Int) {

    }

    @PostMapping("/walk")
    fun walk() {

    }

    @GetMapping("/walk/{month}")
    fun getWalk(@PathVariable month: Int) {

    }
}